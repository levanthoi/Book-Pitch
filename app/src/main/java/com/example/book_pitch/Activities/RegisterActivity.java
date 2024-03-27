package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = RegisterActivity.class.getName();
    private EditText phoneNumberText;
    private EditText nameText;
    private EditText addressText;
    private Button signUpBtn;
    private String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phoneNumberText = findViewById(R.id.editTextPhoneNumber);
        nameText = findViewById(R.id.editTextName);
        addressText = findViewById(R.id.editTextAddress);
        signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                phoneNumber = "+84999999999";
                phoneNumber = phoneNumberText.getText().toString().trim();
                if(phoneNumber.isEmpty() || phoneNumber.length() < 10){
                    phoneNumberText.setError("Vui lòng nhập đúng số điện thoại !");
                    phoneNumberText.requestFocus();
                    return;
                }
                onClickSendOtpCode(phoneNumber);
            }
        });
        mAuth = FirebaseAuth.getInstance();

    }
    private void onClickSendOtpCode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + phoneNumber.substring(1))       // Số điện thoại cần xác thực
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
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

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

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(RegisterActivity.this, "Mã OTP nhập vào không chính xác", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void gotoVerifyOTPActivity(String phoneNumber, String verificationId) {
        Intent intent = new Intent(this, VerifyOTPActivity.class);
        intent.putExtra("mPhoneNumber", phoneNumber);
        intent.putExtra("mVerificationId", verificationId);
        startActivity(intent);
    }
}