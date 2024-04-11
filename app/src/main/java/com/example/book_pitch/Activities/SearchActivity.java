package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.book_pitch.Adapters.PopularAdapter;
import com.example.book_pitch.Adapters.WrapContentGridLayoutManager;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements PopularAdapter.PopularAdapterOnClickHandler {
    private EditText editQuery;
    private RecyclerView rcl_search;
    private FirebaseFirestore firestore;
    private PopularAdapter popularAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        firestore = FirebaseFirestore.getInstance();
        getWindow().setStatusBarColor(Color.parseColor("#198754"));

        rcl_search = findViewById(R.id.rcl_search);

        handleToolbar();
        handleSearch("");
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

        editQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Lấy từ khóa tìm kiếm từ EditText
                    String keyword = editQuery.getText().toString().trim();
                    if (!keyword.isEmpty()) {
                        // Thực hiện tìm kiếm
                        Log.d("test", keyword);
                        handleSearch(keyword);
//                        popularAdapter.startListening();
                    }
                    return true;
                }
                return false;
            }
        });

        editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString().trim();
                handleSearch(keyword);
            }
        });
    }

    private void handleSearch(String keyword) {
        rcl_search.setLayoutManager(new WrapContentGridLayoutManager(this, 2));
        Query query = firestore.collection("stadiums");

        if(keyword != null && !keyword.isEmpty()){
//            List<String> searchTerms = new ArrayList<>();
//            for (String term : keyword) {
//                searchTerms.add(term.trim());
//            }
            query = query.orderBy("title").startAt(keyword);
        }


        FirestoreRecyclerOptions<Stadium> options = new FirestoreRecyclerOptions.Builder<Stadium>()
                .setQuery(query, Stadium.class)
                .build();

        popularAdapter = new PopularAdapter(options, this);
        popularAdapter.startListening();
//        SnapHelper helper = new LinearSnapHelper();
//        helper.attachToRecyclerView(rcl_search);
        popularAdapter.notifyDataSetChanged();
        rcl_search.setAdapter(popularAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Bắt đầu lắng nghe sự kiện từ Firestore khi Fragment được hiển thị
        popularAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Dừng lắng nghe sự kiện từ Firestore khi Fragment bị ẩn
        popularAdapter.stopListening();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // Kiểm tra xem nút back đã được nhấn chưa
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Stadium stadium) {
        Intent intent = new Intent(this, DetailPitchActivity.class);
        Gson gson = new Gson();
        intent.putExtra("stadium", gson.toJson(stadium));
        startActivity(intent);
    }
}