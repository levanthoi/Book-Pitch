package com.example.book_pitch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book_pitch.Models.Bill;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.AndroidUtil;
import com.example.book_pitch.Utils.Helper;
import com.example.book_pitch.Utils.Helpers.AppInfo;
import com.example.book_pitch.Utils.Helpers.CreateOrder;
import com.google.gson.Gson;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

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
        //Khởi tạo ZPDK
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);
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
            String amount = "10000";
            @Override
            public void onClick(View v) {
                CreateOrder orderApi = new CreateOrder();
                try {
                    JSONObject data = orderApi.createOrder(bill!=null ? bill.getPrice().getPrice() : "200000");
                    String code = data.getString("returncode");

                    if (code.equals("1")) {

                        String token = data.getString("zptranstoken");
                        Log.d("TỌKEN", token);
//                    String token = AppInfo.MAC_KEY;

                        ZaloPaySDK.getInstance().payOrder(PaymentActivity.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                                AndroidUtil.showToast(PaymentActivity.this, "Thanh toán thành công");
                            }

                            @Override
                            public void onPaymentCanceled(String zpTransToken, String appTransID) {
                                AndroidUtil.showToast(PaymentActivity.this, "Thanh toán bị hủy");
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                                AndroidUtil.showToast(PaymentActivity.this, "Thanh toán thất bại");
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
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