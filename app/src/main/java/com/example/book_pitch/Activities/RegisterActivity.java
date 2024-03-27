package com.example.book_pitch.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends Activity {
    private FirebaseAuth mAuth;
    private static final String TAG = RegisterActivity.class.getName();
    private EditText phoneNumberText;
    private EditText nameText;
    private EditText addressText;
    private Button signUpBtn;
    private String phoneNumber;
    private TextView signInBtn;
    private ProgressBar progressBar;
    private CheckBox checkBox;
    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phoneNumberText = findViewById(R.id.editTextPhoneNumber);
        nameText = findViewById(R.id.editTextName);
        addressText = findViewById(R.id.editTextAddress);
        signUpBtn = findViewById(R.id.signUpBtn);
        signInBtn = findViewById(R.id.signInBtn);
        progressBar = findViewById(R.id.progressbar);
        checkBox = findViewById(R.id.checkBox);
        fireStore = FirebaseFirestore.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                phoneNumber = "+84999999999";
                phoneNumber = "+84" + phoneNumberText.getText().toString().trim();
                String displayName = nameText.getText().toString();
                String address = addressText.getText().toString();

                if(phoneNumber.isEmpty() || phoneNumber.length() < 9){
                    phoneNumberText.setError("Vui lòng nhập đúng số điện thoại !");
                    progressBar.setVisibility(View.GONE);
                    phoneNumberText.requestFocus();
                    return;
                }
                if(displayName.isEmpty()){
                    nameText.setError("Vui lòng nhập tên!");
                    progressBar.setVisibility(View.GONE);
                    nameText.requestFocus();
                    return;
                }
                if(address.isEmpty()){
                    addressText.setError("Vui lòng nhập địa chỉ !");
                    progressBar.setVisibility(View.GONE);
                    addressText.requestFocus();
                    return;
                }
                if(!checkBox.isChecked()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng đồng ý với chính sách & điều khoản trước khi đăng ký!", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.VISIBLE);
                fireStore.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    boolean phoneNumberExists = false;
                                    for (DocumentSnapshot document : task.getResult()) {
                                        if (document.getId().equals(phoneNumber)) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegisterActivity.this, "Số điện thoại đã được đăng ký", Toast.LENGTH_LONG).show();
                                            phoneNumberExists = true;
                                            return;
                                        }
                                    }
                                    if (!phoneNumberExists) {
                                        onClickSendOtpCode(phoneNumber, displayName, address);
                                    }
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });

            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginPhoneNumberActivity.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();

    }
    private void onClickSendOtpCode(String phoneNumber, String displayName, String address) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Số điện thoại cần xác thực
                        .setTimeout(60L, TimeUnit.SECONDS) // Thời gian chờ để xác thực (tùy chọn)
                        .setActivity(this)                 // Activity hiện tại
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                                Log.d(TAG, "onVerificationCompleted:" + credential);

                                signInWithPhoneAuthCredential(credential, phoneNumber, displayName, address);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.w(TAG, "onVerificationFailed", e);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                System.out.println(e.getMessage());
                                // Show a message and update the UI
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                Log.d(TAG, "onCodeSent:" + verificationId);

                                // Save verification ID and resending token so we can use them later
                                super.onCodeSent(verificationId, token);
                                String displayName = nameText.getText().toString();
                                String address = addressText.getText().toString();
                                gotoVerifyOTPActivity(phoneNumber, verificationId);
                            }
                        })           // Callbacks để xử lý kết quả xác thực
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential,String phoneNumber, String displayName, String address) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            // Update UI
                            Map<String, Object> user = new HashMap<>();
                            user.put("displayName", displayName);
                            user.put("address", address);
                            user.put("phoneNumber", phoneNumber);
                            user.put("avatar", "");
                            user.put("gender", "");
                            user.put("email" , "");
                            fireStore.collection("users").document(phoneNumber)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent = new Intent(RegisterActivity.this, VerifyOTPActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding document", e);
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    });
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "Đăng ký tài khoản thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void gotoVerifyOTPActivity(String phoneNumber, String verificationId) {
        Intent intent = new Intent(RegisterActivity.this, VerifyOTPActivity.class);
        intent.putExtra("mPhoneNumber", phoneNumber);
        intent.putExtra("mVerificationId", verificationId);
        startActivity(intent);
        finish();
    }

}