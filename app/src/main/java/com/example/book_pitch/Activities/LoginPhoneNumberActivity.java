package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_pitch.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.TimeUnit;

public class LoginPhoneNumberActivity extends Activity {
    private static final String TAG = LoginPhoneNumberActivity.class.getName();
    private ProgressBar progressBar;
    private EditText phoneNumberText;
    private Button loginBtn;
    private TextView registerBtn;
    private FirebaseAuth mAuth;
    private String phoneNumber;
    private FirebaseFirestore fireStore;
    ImageView loginGoogleBtn, loginFacebook;
    GoogleSignInClient mGoogleSignInClient;
    private static final int REQ_ONE_TAP = 2;
    private boolean showOneTapUI = true;
    int RC_SIGN_IN = 1;
    BeginSignInRequest signInRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_number);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        phoneNumberText = findViewById(R.id.editTextPhoneNumber);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.register);
        loginGoogleBtn = findViewById(R.id.loginGoogle);
        fireStore = FirebaseFirestore.getInstance();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                phoneNumber = "+84999999999";
                phoneNumber = "+84" + phoneNumberText.getText().toString().trim();
                if(phoneNumber.isEmpty() || phoneNumber.length() < 9){
                    phoneNumberText.setError("Vui lòng nhập đúng số điện thoại !");
                    progressBar.setVisibility(View.GONE);
                    phoneNumberText.requestFocus();
                } else{
                    Toast.makeText(LoginPhoneNumberActivity.this, "Đang chạy", Toast.LENGTH_SHORT).show();
                    fireStore.collection("users")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        boolean phoneNumberExists = false;
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (document.getId().equals(phoneNumber)) {
                                                phoneNumberExists = true;
                                                onClickSendOtpCode(phoneNumber);
                                            }
                                        }
                                        if (!phoneNumberExists) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(LoginPhoneNumberActivity.this, "Số điện thoại chưa được đăng ký", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPhoneNumberActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
//        LOGIN GOOGLE
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        loginGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginGoogle();
            }
        });
//        signInClient = Identity.getSignInClient(this);
//        signInRequest = BeginSignInRequest.builder()
//                .setGoogleIdTokenRequestOptions(
//                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                                .setSupported(true)
//                                .setServerClientId(getString(R.string.default_web_client_id))
//                                .setFilterByAuthorizedAccounts(true)
//                                .build())
//                .build();
//
//        loginGoogle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signInClient.beginSignIn(signInRequest)
//                        .addOnSuccessListener(new OnSuccessListener<BeginSignInResult>() {
//                            @Override
//                            public void onSuccess(BeginSignInResult beginSignInResult) {
//                                // Xử lý thành công
////                                PendingIntent pendingIntent = beginSignInResult.getPendingIntent();
////                                try {
////                                    // Start the intent sender to launch the One Tap UI.
////                                    startIntentSenderForResult(pendingIntent.getIntentSender(),REQ_ONE_TAP, null,0, 0, 0);
//                                    Toast.makeText(LoginPhoneNumberActivity.this, "thanh cong", Toast.LENGTH_SHORT).show();
////                                } catch (IntentSender.SendIntentException e) {
////                                    // Xử lý khi gặp lỗi
////                                    Log.e(TAG, "Error starting One Tap UI: " + e.getMessage());
////                                    Toast.makeText(LoginPhoneNumberActivity.this, "Loi", Toast.LENGTH_SHORT).show();
////                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Xử lý khi gặp lỗi
//                                Log.e(TAG, "Failed to begin One Tap flow: " + e.getMessage());
//                            }
//                        });
//
//            }
//        });
    }

    private void loginGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try{
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Signed In success", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(LoginPhoneNumberActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            gotoMainActivity();
                        } else {
                            Toast.makeText(LoginPhoneNumberActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    ;
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_ONE_TAP:
//                try {
//                    SignInClient oneTapClient = null;
//                    SignInCredential googleCredential = oneTapClient.getSignInCredentialFromIntent(data);
//                    String idToken = googleCredential.getGoogleIdToken();
//                    if (idToken !=  null) {
//                        // Got an ID token from Google. Use it to authenticate
//                        // with Firebase.
//                        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
//                        mAuth.signInWithCredential(firebaseCredential)
//                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if (task.isSuccessful()) {
//                                            // Sign in success, update UI with the signed-in user's information
//                                            Log.d(TAG, "signInWithCredential:success");
//                                            gotoMainActivity();
//                                        } else {
//                                            // If sign in fails, display a message to the user.
//                                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                                        }
//                                    }
//                                });
//                    }
//                } catch (ApiException e) {
//                    // ...
//                }
//                break;
//        }
//    }
    private void onClickSendOtpCode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Số điện thoại cần xác thực
                        .setTimeout(60L, TimeUnit.SECONDS) // Thời gian chờ để xác thực (tùy chọn)
                        .setActivity(this)                 // Activity hiện tại
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                                Log.d(TAG, "onVerificationCompleted:" + credential);

                                signInWithPhoneAuthCredential(credential);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.w(TAG, "onVerificationFailed", e);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginPhoneNumberActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                // Show a message and update the UI
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                Log.d(TAG, "onCodeSent:" + verificationId);

                                // Save verification ID and resending token so we can use them later
                                super.onCodeSent(verificationId, token);
                                gotoVerifyOTPActivity(phoneNumber, verificationId);
                            }
                        })           // Callbacks để xử lý kết quả xác thực
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            gotoMainActivity();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginPhoneNumberActivity.this, "Đăng ký tài khoản thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    private void gotoMainActivity() {
        Intent intent = new Intent(LoginPhoneNumberActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void gotoVerifyOTPActivity(String phoneNumber, String verificationId) {
        Intent intent = new Intent(LoginPhoneNumberActivity.this, VerifyOTPActivity.class);
        intent.putExtra("mPhoneNumber", phoneNumber);
        intent.putExtra("mVerificationId", verificationId);
        startActivity(intent);
        finish();
    }
}