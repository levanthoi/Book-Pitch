package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.book_pitch.Models.Review;
import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private EditText comment;
    private RadioGroup radioGroup;
    private RadioButton incognito;

    Button send;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        firestore = FirebaseFirestore.getInstance();

        init();
        handleClick();
    }

    private void init() {
         ratingBar = findViewById(R.id.rating_bar);
         comment = findViewById(R.id.comment);
         incognito = findViewById(R.id.incognito);
        send = findViewById(R.id.send_comment);
    }

    private void handleClick() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Review review = new Review();
                review.setId(String.valueOf(new Date()));
                Float rating = ratingBar.getRating();
                String comm = comment.getText().toString().trim();
                String radioButtonValue = getSelectedRadioButtonValue();
                    if (!comm.isEmpty()) {
                        sendDataToFirestore(rating, comm, radioButtonValue);
                } else {
                    Toast.makeText(ReviewActivity.this, "Vui lòng nhập comment!", Toast.LENGTH_SHORT).show();
                }
            }
            });
    }
    private String getSelectedRadioButtonValue() {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton incognito = findViewById(selectedRadioButtonId);
            return incognito.getText().toString();
        }
        return "";
    }
    private void sendDataToFirestore(float rating, String comm, String radioButtonValue) {
        Map<String, Object> data = new HashMap<>();
        data.put("rating", rating);
        data.put("comment", comment);
        data.put("radioButtonValue", radioButtonValue);

        firestore.collection("review")
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ReviewActivity.this, "Dữ liệu đã được gửi thành công!", Toast.LENGTH_SHORT).show();
                            // Xóa nội dung đã nhập sau khi gửi thành công
                            comment.setText("");
                            ratingBar.setRating(0);
                            radioGroup.check(R.id.incognito); // Reset radio button về positive
                        } else {
                            Toast.makeText(ReviewActivity.this, "Đã xảy ra lỗi khi gửi dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}