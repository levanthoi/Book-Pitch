package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.book_pitch.Models.Bill;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.Helper;
import com.google.gson.Gson;

public class PaymentActivity extends AppCompatActivity {

    ImageView info_user;
    Button btn_detail_booking;
    Bill bill;
    TextView tv_title, tv_label, tv_address, tv_openTime, tv_dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

//        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle(R.string.payment_info);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handleIntent(getIntent());

        initView();
    }

    private void handleIntent(Intent intent) {
        String key_id = "bill";
        if(intent != null && intent.hasExtra(key_id)){
            String stadium_str = intent.getStringExtra(key_id);
            Gson gson = new Gson();
            bill = gson.fromJson(stadium_str, Bill.class);
        }
    }

    private void initView(){
        info_user = (ImageView) findViewById(R.id.ìnfo_user);
        btn_detail_booking = (Button) findViewById(R.id.btn_detail_booking);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_label = (TextView) findViewById(R.id.tv_label);
        tv_openTime = (TextView) findViewById(R.id.tv_openTime);
        tv_dateTime = (TextView) findViewById(R.id.tv_dateTime);

        if(bill != null) {
            tv_title.setText(bill.getTitle());
            tv_address.setText(bill.getAddress());
            tv_label.setText(bill.getPitch_size() + " - " + bill.getLabel());
            tv_openTime.setText(bill.getPrice().getFrom_time() + " - " + bill.getPrice().getTo_time());
            tv_dateTime.setText(bill.getPrice().getTo_date());
            btn_detail_booking.setText("ĐẶT SÂN (" + Helper.formatNumber(bill.getPrice().getPrice())  + " VNĐ)");
        }


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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // Kiểm tra xem nút back đã được nhấn chưa
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}