package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;

import com.example.book_pitch.Adapters.ViewPagerAdapter;
import com.example.book_pitch.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BookedAndHistoryActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_and_history);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        mViewPager.setAdapter(viewPagerAdapter);
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