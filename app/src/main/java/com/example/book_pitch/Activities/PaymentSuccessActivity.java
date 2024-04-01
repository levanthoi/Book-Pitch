package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.example.book_pitch.R;
import com.example.book_pitch.Utils.AndroidUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PaymentSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        handleClick();
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
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/QR_code_for_mobile_English_Wikipedia.svg/444px-QR_code_for_mobile_English_Wikipedia.svg.png")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            saveImageToStorage(bitmap);
                        }
                    });
                }
            }
        });
    }

    private void saveImageToStorage(Bitmap bitmap) {
        // Tạo thư mục lưu trữ
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");

        // Tạo thư mục nếu nó không tồn tại
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Tạo tên tập tin ảnh
        String fileName = "qr_code_image.jpg";

        // Tạo tệp lưu trữ ảnh
        File file = new File(directory, fileName);

        try {
            // Mở luồng đầu ra để ghi dữ liệu vào tệp
            FileOutputStream fos = new FileOutputStream(file);

            // Chuyển đổi Bitmap thành mảng byte
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            // Đóng luồng đầu ra
            fos.close();

            // Hiển thị thông báo lưu thành công
            AndroidUtil.showToast(this, "Đã lưu ảnh thành công");
        } catch (IOException e) {
            // Xử lý nếu có lỗi xảy ra trong quá trình lưu ảnh
            e.printStackTrace();
            AndroidUtil.showToast(this, "Không thể lưu ảnh");
        }
    }

}