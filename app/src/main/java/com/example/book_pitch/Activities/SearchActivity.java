package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.book_pitch.R;

public class SearchActivity extends AppCompatActivity {
    EditText editQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        handleToolbar();
    }

    private void handleToolbar() {
        Toolbar toolbar = findViewById(R.id.toolabr_search);
        editQuery = findViewById(R.id.editQuery);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Hiển thị bàn phím ảo
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        editQuery.requestFocus();

        // Bắt sự kiện khi EditText mất focus
        editQuery.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("FOCUS", String.valueOf(hasFocus));
                // Nếu không còn focus, ẩn bàn phím
                if (!hasFocus) {
                    imm.hideSoftInputFromWindow(editQuery.getWindowToken(), 0);
                }
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