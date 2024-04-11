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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.book_pitch.Models.Bill;
import com.example.book_pitch.Models.Pitch;
import com.example.book_pitch.Models.Review;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
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
    Bill bill = new Bill();
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

                    bill = document.toObject(Bill.class);
                    render(bill);

                    handleClick(user_id , stadium_id, pitch_id);
                }
            }
        });
    }

    private void render(Bill bill) {
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvNamePitch = findViewById(R.id.tvNamePitch);
        TextView tvAddress = findViewById(R.id.tvAddress);
        TextView tvBeginTime = findViewById(R.id.tvBeginTime);

        ShapeableImageView avatar_stadium = findViewById(R.id.avatar_stadium);
        if(bill != null){
            bill.stadium(new Bill.OnStadiumFetchListener() {
                @Override
                public void onStadiumFetch(Stadium stadium) {
                    tvTitle.setText(stadium.getTitle());
                    tvAddress.setText(stadium.getAddress());
                    Glide.with(ReviewActivity.this).load(stadium.getAvatar()).into(avatar_stadium);
                }
            });

            bill.pitch(new Bill.OnPitchFetchListener() {
                @Override
                public void onPitchFetch(Pitch pitch) {
                    tvNamePitch.setText("Sân " + pitch.getPitch_size() + " - " + "1");
                }
            });
            tvBeginTime.setText(bill.getPrice().getFrom_time() + " - " + bill.getPrice().getTo_time() + ", "+ bill.getPrice().getTo_date());
        }
    }

    private void init() {
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        comment = findViewById(R.id.comment);
        radioGroup = findViewById(R.id.radioGroup);

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
        data.put("id", String.valueOf(System.currentTimeMillis()));
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
                            finish();
                        } else {
                            Toast.makeText(ReviewActivity.this, "Đã xảy ra lỗi khi gửi dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // Kiểm tra xem nút back đã được nhấn chưa
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}