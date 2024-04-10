package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.book_pitch.R;
import com.example.book_pitch.Utils.UserUtil;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FacebookAuthActivity extends LoginPhoneNumberActivity {
    CallbackManager callbackManager;
    FirebaseAuth mAuth;
    FirebaseFirestore fireStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_auth);
        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(FacebookAuthActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(FacebookAuthActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "email, name");
                            // Lấy thông tin người dùng từ Facebook
                            GraphRequest request = GraphRequest.newMeRequest(
                                    AccessToken.getCurrentAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject object, GraphResponse response) {
                                            try {
                                                String email = object.getString("email");
                                                String displayName = object.getString("name");

                                                // Kiểm tra email đã được đăng ký
                                                addNewUserToFirestore(email, displayName);
                                            } catch (JSONException e) {
                                                Toast.makeText(FacebookAuthActivity.this, "Đã xảy ra lỗi khi kiểm tra email", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                            request.setParameters(parameters);
                            request.executeAsync();
                        }
                    }
                });
    }

    private void addNewUserToFirestore(String email, String displayName) {
        FirebaseUser mCurrent = mAuth.getCurrentUser();
        if(mCurrent != null) {
            String userId = mCurrent.getUid();
            Map<String, Object> user = new HashMap<>();
            user.put("uid", userId);
            user.put("email", email);
            user.put("displayName", displayName);
            user.put("address", "");
            user.put("avatar", "");
            user.put("phoneNumber", "");
            user.put("gender", "");
            user.put("loginOption","facebook");

            fireStore.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                boolean foundEmail = false;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    foundEmail = true;
                                    String loginOption = document.getString("loginOption");
                                    // Tiến hành xử lý loginOption ở đây
                                    if (loginOption != null && loginOption.equals("facebook")) {
                                        // Người dùng đã đăng nhập bằng Facebook
                                        Toast.makeText(FacebookAuthActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                        gotoMainActivity();
                                        return; // Thoát khỏi vòng lặp vì đã xác định được kết quả
                                    }
                                }
                                // Nếu không tìm thấy email hoặc không tìm thấy "loginOption" là "Facebook"
                                if (!foundEmail) {
                                    // Thêm dữ liệu vào Firestore
                                    fireStore.collection("users").document(userId)
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(FacebookAuthActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                                    gotoMainActivity();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(FacebookAuthActivity.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(FacebookAuthActivity.this, "Tài khoản đã được đăng ký!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Xử lý khi truy vấn Firestore không thành công
                            }
                        }
                    });
        }
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(FacebookAuthActivity.this, MainActivity.class);
        startActivity(intent);
        // UserUtil.storeUser(FacebookAuthActivity.this);
    }
}