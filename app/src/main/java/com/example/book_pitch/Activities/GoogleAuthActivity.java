package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.book_pitch.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class GoogleAuthActivity extends LoginPhoneNumberActivity {
    private static final String TAG = GoogleAuthActivity.class.getName();
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;
    GoogleSignInClient mGoogleSignInClient;
    int RC_GOOGLE_SIGN_IN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_auth);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        loginGoogle();
    }
    private void loginGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try{
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            FirebaseGoogleAuth(acc);
        } catch(ApiException e) {
            Toast.makeText(this, "Signed In failed", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }
    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                            fireStore.collection("users")
                                    .whereEqualTo("email", account.getEmail())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                if (!task.getResult().isEmpty()) {
                                                    Toast.makeText(GoogleAuthActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                                    gotoMainActivity();
                                                    return;
                                                } else {
                                                    FirebaseUser mCurrent = mAuth.getCurrentUser();
                                                    if(mCurrent != null) {
                                                        String userId = mCurrent.getUid();
                                                        Map<String, Object> user = new HashMap<>();
                                                        user.put("uid", userId);
                                                        user.put("email", account.getEmail());
                                                        user.put("displayName", account.getDisplayName());
                                                        user.put("address", "");
                                                        user.put("avatar", "");
                                                        user.put("phoneNumber", "");
                                                        user.put("gender", "");
                                                        user.put("loginOption","google");
                                                        // Thêm dữ liệu vào Firestore
                                                        fireStore.collection("users").document(userId)
                                                                .set(user)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        gotoMainActivity();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Log.w(TAG, "Error adding document", e);
                                                                    }
                                                                });
                                                        Toast.makeText(GoogleAuthActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                                        gotoMainActivity();
                                                    }
                                                }
                                            } else {
                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                            }
                                        }

                                    });
                        } else {
                            Toast.makeText(GoogleAuthActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void gotoMainActivity() {
        Intent intent = new Intent(GoogleAuthActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}