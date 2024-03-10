package com.example.book_pitch.Activities;

import static android.content.ContentValues.TAG;

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
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView tvPhoneNumber;
    private String mPhoneNumber;
    private String mVerificationId;
    private Button sendOtpBtn;
    private Button reSendOtpBtn;
    private EditText otp1;
    private EditText otp2;
    private EditText otp3;
    private EditText otp4;
    private EditText otp5;
    private EditText otp6;
    private PhoneAuthProvider.ForceResendingToken mToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp_activity);
        progressBar.findViewById(R.id.progressbar);
        tvPhoneNumber.findViewById(R.id.tvPhoneNumber);
        sendOtpBtn.findViewById(R.id.sendOtpBtn);
        reSendOtpBtn.findViewById(R.id.reSendOtpBtn);
        progressBar.setVisibility(View.GONE);
        sendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String strOtp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString()+ otp5.getText().toString() + otp6.getText().toString();
                onClickSendOTPCode(strOtp);
            }
        });
        reSendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickReSendOtp();
            }
        });
        getDataIntent();
        tvPhoneNumber.setText(mPhoneNumber);

        mAuth = FirebaseAuth.getInstance();

    }
    private void getDataIntent() {
        mPhoneNumber = getIntent().getStringExtra("phoneNumber");
        mVerificationId = getIntent().getStringExtra("verificationId");
    }
    private void onClickSendOTPCode(String strOtp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, strOtp);
        signInWithPhoneAuthCredential(credential);
    }
    private void onClickReSendOtp() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mPhoneNumber)       // Số điện thoại cần xác thực
                        .setTimeout(60L, TimeUnit.SECONDS) // Thời gian chờ để xác thực (tùy chọn)
                        .setActivity(this)                 // Activity hiện tại
                        .setForceResendingToken(mToken)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                                Log.d(TAG, "onVerificationCompleted:" + credential);

                                signInWithPhoneAuthCredential(credential);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.w(TAG, "onVerificationFailed", e);
                                Toast.makeText(VerifyOTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                                // Show a message and update the UI
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                // The SMS verification code has been sent to the provided phone number, we
                                // now need to ask the user to enter the code and then construct a credential
                                // by combining the code with a verification ID.
                                Log.d(TAG, "onCodeSent:" + verificationId);

                                // Save verification ID and resending token so we can use them later
                                super.onCodeSent(verificationId, token);
                                mVerificationId = verificationId;
                                mToken = token;
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
                            Intent intent = new Intent(VerifyOTPActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(VerifyOTPActivity.this, "Mã OTP nhập vào không chính xác", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}