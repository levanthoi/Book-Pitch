package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.book_pitch.Adapters.TabViewBookedAdapter;
import com.example.book_pitch.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BookedAndHistoryActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private TabViewBookedAdapter tabViewBookedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_and_history);

        getSupportActionBar().setTitle(R.string.history_and_booked);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        TabViewBookedAdapter tabViewBookedAdapter = new TabViewBookedAdapter(this);

        mViewPager.setAdapter(tabViewBookedAdapter);
        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
            switch(position){
                case 0:
                    tab.setText("Sân đang đặt");
                    break;
                case 1:
                    tab.setText("Lịch sử đặt sân");
                    break;
            }
        }).attach();
    }
}