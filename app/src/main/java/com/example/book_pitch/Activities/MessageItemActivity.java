package com.example.book_pitch.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.book_pitch.Models.MessageGroup;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.google.gson.Gson;

public class MessageItemActivity extends AppCompatActivity {
    private MessageGroup messageGroup;
    private ImageButton back_btn;
    private TextView other_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);
        handleIntent(getIntent());
        handleClick();
        handleHeader();
    }
    private void handleIntent(Intent intent) {
        String key_id = "messageGroup";

        if(intent != null && intent.hasExtra(key_id)){
            String messageGroupJson = intent.getStringExtra(key_id);
            Gson gson = new Gson();
            messageGroup = gson.fromJson(messageGroupJson, MessageGroup.class);


        }
    }
    private void handleClick(){
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void handleHeader(){
        // Tìm TextView tương ứng trong layout fragment_chat
        other_username = findViewById(R.id.other_username);
        // Đặt nội dung mới cho TextView
        other_username.setText(messageGroup.getName());
    }

}