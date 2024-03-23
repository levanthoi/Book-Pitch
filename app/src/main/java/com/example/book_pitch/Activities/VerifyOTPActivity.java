package com.example.book_pitch.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView tvPhoneNumber;
    private String mPhoneNumber;
    private String mVerificationId;
    private String mDisplayName;
    private String mAddress;
    private Button sendOtpBtn;
    private TextView reSendOtpBtn;
    private EditText otp1, otp2, otp3 ,otp4, otp5, otp6;
    private PhoneAuthProvider.ForceResendingToken mToken;
    private int selectedETPosition = 0;
    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp_activity);
        progressBar = findViewById(R.id.progressbar);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        sendOtpBtn = findViewById(R.id.sendOtpBtn);
        reSendOtpBtn = findViewById(R.id.reSendOtpBtn);
        otp1 = findViewById(R.id.inputCode1);
        otp2 = findViewById(R.id.inputCode2);
        otp3 = findViewById(R.id.inputCode3);
        otp4 = findViewById(R.id.inputCode4);
        otp5 = findViewById(R.id.inputCode5);
        otp6 = findViewById(R.id.inputCode6);
        fireStore = FirebaseFirestore.getInstance();

        sendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strOtp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString()+ otp5.getText().toString() + otp6.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                onClickSendOTPCode(strOtp);
            }
        });
        reSendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickReSendOtp();
            }
        });
        otp1.addTextChangedListener(textWatcher);
        otp2.addTextChangedListener(textWatcher);
        otp3.addTextChangedListener(textWatcher);
        otp4.addTextChangedListener(textWatcher);
        otp5.addTextChangedListener(textWatcher);
        otp6.addTextChangedListener(textWatcher);

        getDataIntent();

        tvPhoneNumber.setText(mPhoneNumber);

        mAuth = FirebaseAuth.getInstance();

    }
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() > 0) {
                if (selectedETPosition < 6) {
                    selectedETPosition++;
                }
                switch (selectedETPosition) {
                    case 1:
                        otp2.requestFocus();
                        break;
                    case 2:
                        otp3.requestFocus();
                        break;
                    case 3:
                        otp4.requestFocus();
                        break;
                    case 4:
                        otp5.requestFocus();
                        break;
                    case 5:
                        otp6.requestFocus();
                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL && selectedETPosition > 0) {
            switch (selectedETPosition) {
                case 6:
                    otp5.requestFocus();
                    selectedETPosition = 5;
                    break;
                case 5:
                    otp4.requestFocus();
                    selectedETPosition = 4;
                    break;
                case 4:
                    otp3.requestFocus();
                    selectedETPosition = 3;
                    break;
                case 3:
                    otp2.requestFocus();
                    selectedETPosition = 2;
                    break;
                case 2:
                    otp1.requestFocus();
                    selectedETPosition = 1;
                    break;
                case 1:
                    selectedETPosition = 0;
                    break;
                default:
                    break;
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void getDataIntent() {
        mPhoneNumber = getIntent().getStringExtra("mPhoneNumber");
        mVerificationId = getIntent().getStringExtra("mVerificationId");
        mDisplayName = getIntent().getStringExtra("mDisplayName");
        mAddress = getIntent().getStringExtra("mAddress");
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
                            // Update UI
                            Map<String, Object> user = new HashMap<>();
                            user.put("displayName", mDisplayName);
                            user.put("address", mAddress);
                            user.put("phoneNumber", mPhoneNumber);
                            user.put("avatar", "");
                            user.put("gender", "");
                            fireStore.collection("users").document(mPhoneNumber)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent = new Intent(VerifyOTPActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding document", e);
                                        }
                                    });
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