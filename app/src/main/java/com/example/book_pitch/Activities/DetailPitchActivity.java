package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.book_pitch.Adapters.SliderAdapter;
import com.example.book_pitch.Fragment.BottomSheetFragment;
import com.example.book_pitch.Models.Pitch;
import com.example.book_pitch.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class DetailPitchActivity extends AppCompatActivity {
    private ViewPager2 sliderPitch;
    Button btn_booking;
    private Handler slideHandler = new Handler();
    List<String> slider = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pitch);

        btn_booking = findViewById(R.id.btn_booking);

        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet();
            }
        });

        initView();
        getData();
        render();

    }

    public void openBottomSheet(){
        List<Pitch> list = new ArrayList<>();
        for(int i=0; i<3;i++){
            list.add(new Pitch(i, "San "+ i, 7));
        }
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment(this, list);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
    private void getData(){
        slider.add("https://www.sanbongro.com.vn/sources/utils/timthumb.php?src=https://www.sanbongro.com.vn/uploads/supply/2019/01/07/thi_cong_san_bong_da_mini_5_nguoi_tiet_kiem.jpg&w=480&h=480&zc=2&a=t&wm=0");
        slider.add("https://www.sanbongro.com.vn/sources/utils/timthumb.php?src=https://www.sanbongro.com.vn/uploads/supply/2019/01/10/thi_cong_san_bong_da_11_nguoi_cao_cap_voi_den_led_400w_usa.jpg&w=480&h=480&zc=2&a=t&wm=t");
        slider.add("https://www.sanbongro.com.vn/sources/utils/timthumb.php?src=https://www.sanbongro.com.vn/uploads/supply/2019/01/10/thi_cong_san_bong_da_11_nguoi_pho_bien_voi_den_led_400w_usa.jpg&w=480&h=480&zc=2&a=t&wm=t");
        slider.add("https://www.sanbongro.com.vn/sources/utils/timthumb.php?src=https://www.sanbongro.com.vn/uploads/supply/2019/01/07/lam_san_bong_da_mini_7_nguoi_tiet_kiem.jpg&w=480&h=480&zc=2&a=t&wm=t");
    }

    private void initView(){
        sliderPitch = findViewById(R.id.sliderPitch);
    }

    private void render(){
        sliderPitch.setAdapter(new SliderAdapter(slider, sliderPitch));
        sliderPitch.setClipToPadding(false);
        sliderPitch.setClipChildren(false);
        sliderPitch.setOffscreenPageLimit(4);
        sliderPitch.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_IF_CONTENT_SCROLLS);

        CompositePageTransformer cpt = new CompositePageTransformer();
        cpt.addTransformer(new MarginPageTransformer(40));
        cpt.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r*0.15f);
            }
        });

        sliderPitch.setPageTransformer(cpt);
        sliderPitch.setCurrentItem(1);
        sliderPitch.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderRunnable);
            }
        });
    }
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            sliderPitch.setCurrentItem(sliderPitch.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable, 2000);
    }
}