package com.example.book_pitch.Activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.book_pitch.Adapters.GenderAdapter;
import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private ImageView userAvatar;
    private EditText userDisplayName;
    private EditText userAddress;
    private EditText userPhoneNumber;
    private EditText userEmail;
    private Spinner userGender;
    private Button saveBtn;
    private GenderAdapter genderAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String mEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.edit_profile);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        userAvatar = findViewById(R.id.userAvatar);
        userDisplayName = findViewById(R.id.userDisplayName);
        userAddress = findViewById(R.id.userAddress);
        userPhoneNumber = findViewById(R.id.userPhoneNumber);
        userEmail = findViewById(R.id.userEmail);
        userGender = findViewById(R.id.userGender);
        saveBtn = findViewById(R.id.saveBtn);
        mAuth = FirebaseAuth.getInstance();
        mEmail = getIntent().getStringExtra("email");
        List<String> genders = new ArrayList<>();
        genders.add("Nam");
        genders.add("Nữ");
        genders.add("Khác");
        genderAdapter = new GenderAdapter(this, genders);
        userGender.setAdapter(genderAdapter);
        db = FirebaseFirestore.getInstance();
        userPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Không cần xử lý trước sự thay đổi văn bản
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Không cần xử lý trong quá trình thay đổi văn bản
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String phoneNumber = editable.toString();
                if (!phoneNumber.startsWith("+84")) {
                    phoneNumber = "+84" + phoneNumber;
                    // Cập nhật lại văn bản trong EditText
                    userPhoneNumber.setText(phoneNumber);
                    // Di chuyển con trỏ về cuối văn bản
                    userPhoneNumber.setSelection(phoneNumber.length());
                }
            }
        });
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser != null) {
            String userId = mUser.getUid();
            db.collection("users").document(userId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                    String displayName = document.getString("displayName");
                                    String address = document.getString("address");
                                    String phoneNumberStr = document.getString("phoneNumber");
                                    String email = document.getString("email");
                                    String gender = document.getString("gender");

                                    userDisplayName.setText(displayName);
                                    userPhoneNumber.setText(phoneNumberStr);
                                    userAddress.setText(address);
                                    userEmail.setText(email);
                                    if (gender != null) {
                                        int index = genders.indexOf(gender);
                                        if (index != -1) {
                                            userGender.setSelection(index);
                                        }
                                    }
                            } else {
                                Toast.makeText(EditProfileActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
//        String phoneNumber = mAuth.getCurrentUser().getPhoneNumber();
//        String email = mAuth.getCurrentUser().getEmail();
//        String queryField = (phoneNumber != null) ? "phoneNumber" : "email";
//        String queryValue = (phoneNumber != null) ? phoneNumber : email;
//        if(queryValue != null) {
//            db.collection("users")
//                    .whereEqualTo(queryField,queryValue)
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    String displayName = document.getString("displayName");
//                                    String address = document.getString("address");
//                                    String phoneNumberStr = document.getString("phoneNumber");
//                                    String email = document.getString("email");
//                                    String gender = document.getString("gender");
//
//                                    userDisplayName.setText(displayName);
//                                    userPhoneNumber.setText(phoneNumberStr);
//                                    userAddress.setText(address);
//                                    userEmail.setText(email);
//                                    if (gender != null) {
//                                        int index = genders.indexOf(gender);
//                                        if (index != -1) {
//                                            userGender.setSelection(index);
//                                        }
//                                    }
//                                }
//                            } else {
//                                Toast.makeText(EditProfileActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = mAuth.getCurrentUser().getUid();
                if(userId != null) {
                    DocumentReference userRef = db.collection("users").document(userId);
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("displayName", userDisplayName.getText().toString());
                    updates.put("address", userAddress.getText().toString());
                    updates.put("phoneNumber", userPhoneNumber.getText().toString());
                    updates.put("avatar", "");
                    updates.put("gender", userGender.getSelectedItem().toString());
                    userRef.update(updates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EditProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditProfileActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(EditProfileActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}