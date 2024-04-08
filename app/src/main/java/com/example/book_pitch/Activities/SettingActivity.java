package com.example.book_pitch.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.book_pitch.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {
    Button logoutBtn;
    FirebaseAuth mAuth;
    LinearLayout deleteAccount, about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        logoutBtn = findViewById(R.id.logoutBtn);
        deleteAccount = findViewById(R.id.deleteAccount);
        about = findViewById(R.id.about);
        mAuth = FirebaseAuth.getInstance();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.setting);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                GoogleSignIn.getClient(SettingActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();
                LoginManager.getInstance().logOut();
                Intent i = new Intent(SettingActivity.this, LoginPhoneNumberActivity.class);
                startActivity(i);
                finish();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("Yêu cầu xóa tài khoản");
        builder.setMessage("Chúng tôi rất lấy làm tiếc khi bạn muốn rời SPORTME, nhưng xin lưu ý các tài khoản dã bị xóa sẽ không được mở trở lại. Bạn có chắc muốn thực hiện hành động này?");
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SettingActivity.this, "Đã xác nhận", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(i);
            }
        });
    }
}