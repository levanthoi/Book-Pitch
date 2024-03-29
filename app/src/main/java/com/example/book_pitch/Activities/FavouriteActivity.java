package com.example.book_pitch.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.book_pitch.R;

public class FavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        getSupportActionBar().setTitle(R.string.favourite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}