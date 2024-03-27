package com.example.book_pitch.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.book_pitch.Adapters.SliderAdapter;
import com.example.book_pitch.R;

import java.util.ArrayList;
import java.util.List;

public class SliderFragment extends Fragment {
    private ViewPager2 slider_banner;
    List<String> sliders = new ArrayList<>();

    private Handler slideHandler = new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider, container, false);

        initView(view);
        initData();
        render();
        return view;
    }


    private void initView(View view) {
        slider_banner = view.findViewById(R.id.slider_banner);
    }

    private void initData() {
        sliders.add("https://images2.thanhnien.vn/thumb_w/640/528068263637045248/2023/12/31/anhrao1-ol-17040354163761882197397.jpg");
        sliders.add("https://caodang.fpt.edu.vn/wp-content/uploads/2024/02/1-2-1024x683.png");
        sliders.add("https://image.sggp.org.vn/Uploaded/2024/noktju/2023_10_30/mobile-800-400-6274.jpg");
    }

    private void render(){
        slider_banner.setAdapter(new SliderAdapter(sliders, slider_banner));
        slider_banner.setClipToPadding(false);
        slider_banner.setClipChildren(false);
        slider_banner.setOffscreenPageLimit(3);
        slider_banner.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_IF_CONTENT_SCROLLS);

        CompositePageTransformer cpt = new CompositePageTransformer();
        cpt.addTransformer(new MarginPageTransformer(40));
        cpt.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r*0.15f);
            }
        });

        slider_banner.setPageTransformer(cpt);
//        slider_banner.setCurrentItem(1);
//        slider_banner.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                slideHandler.removeCallbacks(sliderRunnable);
//            }
//        });
    }
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int current = slider_banner.getCurrentItem();
            if(current < sliders.size() - 1)
                slider_banner.setCurrentItem(current + 1);
            else slider_banner.setCurrentItem(0);
            slideHandler.postDelayed(this, 2000);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable, 2000);
        slider_banner.setCurrentItem(0);
    }
}