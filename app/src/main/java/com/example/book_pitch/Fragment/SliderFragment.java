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
    List<String> sliders;

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
        sliders = new ArrayList<>();
        sliders.add("https://www.google.com/url?sa=i&url=https%3A%2F%2Fthanhnien.vn%2Fra-mat-chuyen-trang-giai-bong-da-thanh-nien-sinh-vien-viet-nam-185231231222618865.htm&psig=AOvVaw28_-LtYKRRWOkH3fvaYPc4&ust=1711253432565000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIDD7PrBiYUDFQAAAAAdAAAAABAE");
        sliders.add("https://www.google.com/url?sa=i&url=https%3A%2F%2Fm.youtube.com%2Fwatch%3Fv%3DPOncvElqs2g&psig=AOvVaw28_-LtYKRRWOkH3fvaYPc4&ust=1711253432565000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPitg5vCiYUDFQAAAAAdAAAAABAJ");
        sliders.add("https://www.google.com/url?sa=i&url=https%3A%2F%2Fthanhnien.vn%2Fra-mat-chuyen-trang-giai-bong-da-thanh-nien-sinh-vien-viet-nam-185231231222618865.htm&psig=AOvVaw28_-LtYKRRWOkH3fvaYPc4&ust=1711253432565000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIDD7PrBiYUDFQAAAAAdAAAAABAE");
    }

    private void render(){
        slider_banner.setAdapter(new SliderAdapter(sliders, slider_banner));
        slider_banner.setClipToPadding(false);
        slider_banner.setClipChildren(false);
        slider_banner.setOffscreenPageLimit(4);
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
        slider_banner.setCurrentItem(1);
        slider_banner.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
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
            slider_banner.setCurrentItem(slider_banner.getCurrentItem() + 1);
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
    }
}