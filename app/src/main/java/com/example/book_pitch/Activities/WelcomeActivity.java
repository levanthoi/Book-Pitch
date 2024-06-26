package com.example.book_pitch.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.book_pitch.Adapters.IntroViewAdapter;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.PrefManager;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager2 mViewPager;
    private IntroViewAdapter mPagerAdapter;
    private LinearLayout mDotsLayout;
    private int[] mLayouts;
    private Button mBtnSkip;
    private Button mBtnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (!PrefManager.getInstance(this).isFirstTimeLaunch()) {
//            launchHomeScreen();
//        }

        //Making notification bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);

        mViewPager = findViewById(R.id.view_pager);
        mDotsLayout = findViewById(R.id.layoutDots);
        mBtnSkip = findViewById(R.id.btn_skip);
        mBtnNext = findViewById(R.id.btn_next);

        // Layouts of all welcome slides
        mLayouts = new int[]{R.layout.activity_welcome_slide1, R.layout.activity_welcome_slide2, R.layout.activity_welcome_slide3};
        addBottomDots(0);
        changeStatusBarColor();

        mPagerAdapter = new IntroViewAdapter(mLayouts);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Gọi notifyDataSetChanged() ở đây để cập nhật layout khi trang được chuyển đổi
                mViewPager.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPagerAdapter.notifyDataSetChanged();
                    }
                }, 100);
                addBottomDots(position);
                if (position == mLayouts.length - 1) {
                    mBtnNext.setText(getString(R.string.start));
                    mBtnSkip.setVisibility(View.GONE);
                } else {
                    mBtnNext.setText(getString(R.string.next));
                    mBtnSkip.setVisibility(View.VISIBLE);
                }
            }
        });
        mBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = mViewPager.getCurrentItem();
                if (current < mLayouts.length - 1) {
                    mViewPager.setCurrentItem(current + 1);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }
    private void addBottomDots(int currentPage) {
        mDotsLayout.removeAllViews();

        int colorActive = ContextCompat.getColor(this, R.color.dot_active);
        int colorInactive = ContextCompat.getColor(this, R.color.dot_inactive);

        for (int i = 0; i < mLayouts.length; i++) {
            TextView dot = new TextView(this);
            dot.setText(HtmlCompat.fromHtml("&#8226;", HtmlCompat.FROM_HTML_MODE_LEGACY));
            dot.setTextSize(35);
            dot.setTextColor(i == currentPage ? colorActive : colorInactive);
            mDotsLayout.addView(dot);
        }
    }


    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void launchHomeScreen() {
        PrefManager.getInstance(this).setFirstimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

}
