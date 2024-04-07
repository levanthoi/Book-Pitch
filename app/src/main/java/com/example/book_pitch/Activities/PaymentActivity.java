package com.example.book_pitch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book_pitch.Models.Bill;
import com.example.book_pitch.Models.Pitch;
import com.example.book_pitch.Models.Price;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.AndroidUtil;
import com.example.book_pitch.Utils.FirebaseUtil;
import com.example.book_pitch.Utils.Helper;
import com.example.book_pitch.Utils.Helpers.AppInfo;
import com.example.book_pitch.Utils.Helpers.CreateOrder;
import com.example.book_pitch.Utils.Helpers.Helpers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentActivity extends AppCompatActivity {

    ImageView info_user;
    Button btn_detail_booking;
    TextView tv_title, tv_label, tv_address, tv_openTime, tv_dateTime, current_name, current_phone;
    Stadium stadium;
    Pitch pitch;
    Price price;
    FirebaseFirestore firestore;
    FirebaseAuth mauth;
    static Bill bill = new Bill();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

//        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle(R.string.payment_info);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firestore = FirebaseFirestore.getInstance();
        mauth =  FirebaseAuth.getInstance();

        handleIntent(getIntent());



        initView();
        //Khởi tạo ZPDK
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);
    }

    private void handleIntent(Intent intent) {
        String key_stadium = "stadium";
        String key_pitch= "pitch";
        String key_price = "price";
        if(intent != null && intent.hasExtra(key_stadium) && intent.hasExtra(key_pitch) && intent.hasExtra(key_price)){
            String stadium_str = intent.getStringExtra(key_stadium);
            String pitch_str = intent.getStringExtra(key_pitch);
            String price_str = intent.getStringExtra(key_price);

            Gson gson = new Gson();
            stadium = gson.fromJson(stadium_str, Stadium.class);
            pitch = gson.fromJson(pitch_str, Pitch.class);
            price = gson.fromJson(price_str, Price.class);
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
        current_name = (TextView) findViewById(R.id.current_name);
        current_phone = (TextView) findViewById(R.id.current_phone);

        if(!mauth.getCurrentUser().getDisplayName().isEmpty()){
            current_name.setText(mauth.getCurrentUser().getDisplayName());
        }
        if(!mauth.getCurrentUser().getPhoneNumber().isEmpty()){
            current_phone.setText(mauth.getCurrentUser().getPhoneNumber());
        }

        if(stadium != null && pitch != null && price != null) {
            tv_title.setText(stadium.getTitle());
            tv_address.setText(stadium.getAddress());
            tv_label.setText("Sân "+ pitch.getPitch_size() + " - " + pitch.getLabel());
            tv_openTime.setText(price.getFrom_time() + " - " + price.getTo_time());
            tv_dateTime.setText(price.getFrom_date());
            btn_detail_booking.setText("ĐẶT SÂN (" + Helper.formatNumber(price.getPrice())  + " VNĐ)");
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
                if(isValid()){
                    handlePayment();
                }
            }
        });
    }

    private void handlePayment() {
        bill.setId(Helpers.getAppTransId());
        bill.setPrice(price);
        bill.setDeleted(0);
        bill.setStatus(0);
        bill.setUser_id(FirebaseUtil.currentUserId());
        bill.setPitch_id(pitch.getId());
        bill.setStadium_id(stadium.getId());
        bill.setTransactionToken("");
        bill.setCreatedAt(new Date());
        bill.setUpdatedAt(new Date());

        sendPayment(bill);
    }

    private boolean isValid() {
        RadioButton btn_zalopay = findViewById(R.id.btn_zalopay);
        if(!btn_zalopay.isChecked()) {
//            btn_zalopay.setError("Ở đây này");
            AndroidUtil.showToast(PaymentActivity.this, "Vui lòng chọn phương thức thanh toán");
            return false;
        }

        return true;
    }

    private void sendPayment(Bill bill) {
        CreateOrder orderApi = new CreateOrder();
        try {
            JSONObject data = orderApi.createOrder(bill!=null ? bill.getPrice().getPrice() : "200000");
            String code = data.getString("returncode");
            Log.d("test", data.toString());
            Log.d("test", code);

            if (code.equals("1")) {
                String token = data.getString("zptranstoken");

                ZaloPaySDK.getInstance().payOrder(PaymentActivity.this, token, "demozpdk://app", new ZalopayListener());
            }else{
                Log.d("test", "not 1");
//                handlePaymentFailure(bill, "Không thể tạo: " + data.getString("returnmessage"));
            }

        } catch (Exception e) {
            Log.d("EÊ", "ERRRRR");
            handlePaymentFailure(bill, "not connect");
            e.printStackTrace();
        }
    }

    private class ZalopayListener implements PayOrderListener {

        @Override
        public void onPaymentSucceeded(String appTransID, String transToken, String transactionId) {
            Log.d("test", "Successs");
            bill.setId(appTransID);
            bill.setTransactionToken(transToken);
            bill.setTransactionId(transactionId);
            bill.setStatus(1);

            saveOrderToFirestore(bill);
            AndroidUtil.showToast(PaymentActivity.this, "Thanh toán thành công");
        }

        @Override
        public void onPaymentCanceled(String s, String s1) {
            Log.d("test", "Cancel");
            handlePaymentFailure(bill, "Thanh toán đã hùy");
        }

        @Override
        public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
            Log.d("test", "Errorrr");
            handlePaymentFailure(bill, "Thanh toán không thành công");
        }
    }

    private void handlePaymentFailure(Bill bill, String errorMessage) {
        bill.setStatus(-1); // Đánh dấu đơn hàng thất bại
        saveOrderToFirestore(bill);
        AndroidUtil.showToast(this, errorMessage);
    }

    private void saveOrderToFirestore(Bill bill) {
        bill.setCreatedAt(new Date());
        bill.setUpdatedAt(new Date());

        firestore.collection("bills").document(bill.getId()).set(bill)
                .addOnSuccessListener(v -> {
                    if(bill.getStatus() == 1){
                        Intent intent = new Intent(PaymentActivity.this, PaymentSuccessActivity.class);
                        intent.putExtra("bill", new Gson().toJson(bill));
                        startActivity(intent);
                        finish();
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý lỗi khi lưu vào Firestore
                        Log.d("Failllll FIRESTORE", "FRRRR");
                        AndroidUtil.showToast(PaymentActivity.this, "Lỗi firebase");
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