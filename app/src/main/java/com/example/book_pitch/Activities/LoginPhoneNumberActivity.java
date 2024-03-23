package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.TimeUnit;

public class LoginPhoneNumberActivity extends AppCompatActivity {
    private static final String TAG = LoginPhoneNumberActivity.class.getName();
    private ProgressBar progressBar;
    private EditText phoneNumberText;
    private Button loginBtn;
    private TextView registerBtn;
    private FirebaseAuth mAuth;
    private String phoneNumber;
    private FirebaseFirestore fireStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_number);
        progressBar = findViewById(R.id.progressbar);
        phoneNumberText = findViewById(R.id.editTextPhoneNumber);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.register);
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
                    return;
                }
                fireStore.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    boolean phoneNumberExists = false;
                                    for (DocumentSnapshot document : task.getResult()) {
                                        if (document.getId().equals(phoneNumber)) {
                                            phoneNumberExists = true;
                                            onClickSendOtpCode(phoneNumber);
                                        }
                                    }
                                    if (!phoneNumberExists) {
                                        Toast.makeText(LoginPhoneNumberActivity.this, "Số điện thoại chưa được đăng ký", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });
                progressBar.setVisibility(View.VISIBLE);

            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPhoneNumberActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }
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
    }
}