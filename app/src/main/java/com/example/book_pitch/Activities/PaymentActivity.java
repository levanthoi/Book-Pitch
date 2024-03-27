package com.example.book_pitch.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.book_pitch.R;

public class PaymentActivity extends AppCompatActivity {

    ImageView info_user;
    Button btn_detail_booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

//        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle(R.string.payment_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
    }

    private void initView(){
        info_user = (ImageView) findViewById(R.id.ìnfo_user);
        btn_detail_booking = (Button) findViewById(R.id.btn_detail_booking);

        info_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, EditProfileActivity.class);
                intent.putExtra("source", "PaymentActivity");
                startActivity(intent);
            }
        });

        btn_detail_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, PaymentSuccessActivity.class);
                intent.putExtra("source", "PaymentActivity");
                startActivity(intent);
            }
        });
    }
}