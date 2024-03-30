package com.example.book_pitch.Activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentReference;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setTitle(R.string.edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userAvatar = findViewById(R.id.userAvatar);
        userDisplayName = findViewById(R.id.userDisplayName);
        userAddress = findViewById(R.id.userAddress);
        userPhoneNumber = findViewById(R.id.userPhoneNumber);
        userEmail = findViewById(R.id.userEmail);
        userGender = findViewById(R.id.userGender);
        saveBtn = findViewById(R.id.saveBtn);
        mAuth = FirebaseAuth.getInstance();

        List<String> genders = new ArrayList<>();
        genders.add("Nam");
        genders.add("Nữ");
        genders.add("Khác");
        genderAdapter = new GenderAdapter(this, genders);
        userGender.setAdapter(genderAdapter);
        db = FirebaseFirestore.getInstance();

        db.collection("users")
                .whereEqualTo(FieldPath.documentId(), mAuth.getCurrentUser().getPhoneNumber())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String displayName = document.getString("displayName");
                                String address = document.getString("address");
                                String phoneNumber = document.getString("phoneNumber");
                                String email = document.getString("email");
                                String gender = document.getString("gender");

                                userDisplayName.setText(displayName);
                                userPhoneNumber.setText(phoneNumber);
                                userAddress.setText(address);
                                userEmail.setText(email);
                                if (gender != null) {
                                    int index = genders.indexOf(gender);
                                    if (index != -1) {
                                        userGender.setSelection(index);
                                    }
                                }
                            }
                        } else {
                            // Xử lý khi không thể lấy dữ liệu từ Firestore
                            Toast.makeText(EditProfileActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = mAuth.getCurrentUser().getPhoneNumber();
                DocumentReference docRef = db.collection("users").document(userId);
                Map<String, Object> updates = new HashMap<>();
                updates.put("displayName", userDisplayName.getText().toString());
                updates.put("address", userAddress.getText().toString());
                updates.put("phoneNumber", userPhoneNumber.getText().toString());
                updates.put("avatar", "");
                updates.put("gender", userGender.getSelectedItem().toString());
                docRef.update(updates)
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
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.accountFragment){
            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
            return true;
        }else {
            Intent intent = new Intent(EditProfileActivity.this, PaymentActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
    }

}