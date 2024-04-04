package com.example.book_pitch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book_pitch.Models.Bill;
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
                intent.putExtra("url", "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html"); //bắt buộc, VNPAY cung cấp
                intent.putExtra("tmn_code", "KSPLJVON"); //bắt buộc, VNPAY cung cấp
                intent.putExtra("scheme", "PaymentSuccessActivity"); //bắt buộc, scheme để mở lại app khi có kết quả thanh toán từ mobile banking
                intent.putExtra("is_sandbox", true); //bắt buộc, true <=> môi trường test, true <=> môi trường live

//                VNP_AuthenticationActivity.setSdkCompletedCallback(new VNP_SdkCompletedCallback() {
//                    @Override
//                    public void sdkAction(String action) {
//                        Log.wtf("SplashActivity", "action: " + action);
//                        //action == AppBackAction
//                        //Người dùng nhấn back từ sdk để quay lại
//
//                        //action == CallMobileBankingApp
//                        //Người dùng nhấn chọn thanh toán qua app thanh toán (Mobile Banking, Ví...)
//                        //lúc này app tích hợp sẽ cần lưu lại cái PNR, khi nào người dùng mở lại app tích hợp thì sẽ gọi kiểm tra trạng thái thanh toán của PNR Đó xem đã thanh toán hay chưa.
//
//                        //action == WebBackAction
//                        //Người dùng nhấn back từ trang thanh toán thành công khi thanh toán qua thẻ khi url có chứa: cancel.sdk.merchantbackapp
//
//                        //action == FaildBackAction
//                        //giao dịch thanh toán bị failed
//
//                        //action == SuccessBackAction
//                        //thanh toán thành công trên webview
//                    }
//                });
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