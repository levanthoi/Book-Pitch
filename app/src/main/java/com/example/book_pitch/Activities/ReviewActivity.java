package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.book_pitch.Models.Bill;
import com.example.book_pitch.Models.Review;
import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private EditText comment;
    private RadioGroup radioGroup;

    String id;

    private RadioButton incognito;

    Button send;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        id= getIntent().getStringExtra("id");
        firestore = FirebaseFirestore.getInstance();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Đánh giá sân bóng");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        retrieveBillDataAndSendReview();
        init();
    }
    private void retrieveBillDataAndSendReview() {
        DocumentReference docRef = firestore.collection("bills").document(id);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String user_id = document.getString("user_id");
                    String stadium_id = document.getString("stadium_id");
                    String pitch_id = document.getString("pitch_id");

                    handleClick(user_id , stadium_id, pitch_id);
                }
            }
        });
    }

    private void init() {
        ratingBar = findViewById(R.id.rating_bar);
        comment = findViewById(R.id.comment);
        radioGroup = findViewById(R.id.radioGroup);
        /*ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                Toast.makeText(ReviewActivity.this, "Bạn đã đánh giá: " + rating, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void handleClick(String user_id ,String stadium_id, String pitch_id) {
        Button send = findViewById(R.id.send_comment);
        send.setOnClickListener(v -> {
            String rating = String.valueOf(ratingBar.getRating());
            String comm = comment.getText().toString().trim();
            String status = getSelectedRadioButtonValue();

            if (!comm.isEmpty()) {
                sendDataToFirestore(user_id, stadium_id, pitch_id, rating, comm, status);
            } else {
                Toast.makeText(ReviewActivity.this, "Vui lòng nhập comment!", Toast.LENGTH_SHORT).show();
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
    private void sendDataToFirestore(String user_id ,String stadium_id, String pitch_id, String rating, String comm, String status) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", user_id);
        data.put("stadium_id", stadium_id);
        data.put("pitch_id", pitch_id);
        data.put("rating", rating);
        data.put("comment", comm);
        data.put("status", status);

        firestore.collection("review")
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ReviewActivity.this, "Bạn đã đánh gía thành công!", Toast.LENGTH_SHORT).show();
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