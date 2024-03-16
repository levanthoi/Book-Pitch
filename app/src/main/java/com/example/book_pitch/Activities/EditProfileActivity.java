package com.example.book_pitch.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.book_pitch.R;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setTitle(R.string.edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String source = getIntent().getStringExtra("source");

//        switch (source){
//            case "PaymentActivity":
//
//        }
    }


}