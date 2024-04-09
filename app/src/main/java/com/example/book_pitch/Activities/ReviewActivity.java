package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;

import com.example.book_pitch.Models.Review;
import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class ReviewActivity extends AppCompatActivity {

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
        RatingBar ratingBar = findViewById(R.id.rating_bar);
        EditText comment = findViewById(R.id.comment);
        RadioButton incognito = findViewById(R.id.incognito);
        send = findViewById(R.id.send_comment);
    }

    private void handleClick() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Review review = new Review();
                review.setId(String.valueOf(new Date()));
//                firestore.collection("reviews").document(review.getId()).add(review)
//                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                            }
//                        });
            }
        });
    }
}