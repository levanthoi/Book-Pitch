package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.book_pitch.Models.Bill;
import com.example.book_pitch.Models.Pitch;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.AndroidUtil;
import com.example.book_pitch.Utils.QrCodeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PaymentSuccessActivity extends AppCompatActivity {

    Bill bill = new Bill();
    Bitmap qrCodeBitmap;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        handleIntent(getIntent());
        firestore = FirebaseFirestore.getInstance();

        render();
        renderQrCode();

        handleClick();
    }

    private void handleIntent(Intent intent) {
        String key_id = "bill";
        if(intent != null && intent.hasExtra(key_id)){
            String bill_str = intent.getStringExtra(key_id);
            Gson gson = new Gson();
            bill = gson.fromJson(bill_str, Bill.class);
        }
    }

    private void render() {
        DocumentReference docRef = firestore.collection("stadiums").document(bill.getStadium_id());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        renderStadium(document.toObject(Stadium.class));
                    } else {
                        AndroidUtil.showToast(PaymentSuccessActivity.this, "Không tồn tại sân bóng");
                    }
                } else {
                    AndroidUtil.showToast(PaymentSuccessActivity.this, "Không tồn kết nối tới firebase");
                }
            }
        });

        DocumentReference docRefPitch = firestore.collection("pitches").document(bill.getPitch_id());
        docRefPitch.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        renderPitch(document.toObject(Pitch.class));
                    } else {
                        AndroidUtil.showToast(PaymentSuccessActivity.this, "Không tồn tại sân bóng");
                    }
                } else {
                    AndroidUtil.showToast(PaymentSuccessActivity.this, "Không tồn kết nối tới firebase");
                }
            }
        });


    }

    private void renderStadium(Stadium stadium) {
        TextView title, addresss;

        title = findViewById(R.id.success_title);
        addresss = findViewById(R.id.success_addresss);

        title.setText(stadium.getTitle());
        addresss.setText(stadium.getAddress());

    }

    private void renderPitch(Pitch pitch) {
        TextView pitch_info = findViewById(R.id.success_pitch_info);
        TextView time = findViewById(R.id.success_time);
        TextView date = findViewById(R.id.success_date);

        pitch_info.setText("Sân "+ pitch.getPitch_size() + " - " + pitch.getLabel());
        time.setText(bill.getPrice().getFrom_time() + " - " + bill.getPrice().getTo_time());
        date.setText(bill.getPrice().getTo_date());
    }
    private void renderQrCode() {
        qrCodeBitmap = QrCodeUtil.generateQRCode(bill, 300, 300);
        ImageView img_qrcode = findViewById(R.id.img_qrcode);
        img_qrcode.setImageBitmap(qrCodeBitmap);
    }

    private void handleClick() {
        Button btn_download, btn_close;

        btn_close = findViewById(R.id.btn_close);
        btn_download = findViewById(R.id.btn_download);

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDownload();
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void handleDownload() {
        if(qrCodeBitmap != null)
            saveImageToStorage(qrCodeBitmap);
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/QR_code_for_mobile_English_Wikipedia.svg/444px-QR_code_for_mobile_English_Wikipedia.svg.png")
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if(response.isSuccessful()){
//                    final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            saveImageToStorage(bitmap);
//                        }
//                    });
//                }
//            }
//        });
    }

    private void saveImageToStorage(Bitmap bitmap) {
        OutputStream fos;
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentResolver resolver = getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_" + ".jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "TestFolder");
                Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

                fos = (FileOutputStream) resolver.openOutputStream(Objects.requireNonNull(uri));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);

                AndroidUtil.showToast(PaymentSuccessActivity.this, "Lưu ảnh thành công");
            }
        }catch (Exception e) {
            AndroidUtil.showToast(PaymentSuccessActivity.this, "Không thể lưu ảnh");
            e.printStackTrace();
        }
    }

}