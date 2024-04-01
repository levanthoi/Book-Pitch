package com.example.book_pitch.Activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_pitch.Adapters.SliderAdapter;
import com.example.book_pitch.Fragment.BottomSheetFragment;
import com.example.book_pitch.Fragment.BottomSheetHotline;
import com.example.book_pitch.Models.Pitch;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DetailPitchActivity extends AppCompatActivity {
    private ViewPager2 sliderPitch;
    Button btn_booking, btn_contact, btn_direction;
    Toolbar toolbar;
    TextView title_toolabr;
    ImageView share, heart, option;
    private Handler slideHandler = new Handler();
    List<String> slider = new ArrayList<>();
    Stadium stadium;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pitch);

        getWindow().setStatusBarColor(Color.parseColor("#198754"));

        handleIntent(getIntent());
        initView();
        getData();
        render();
        renderPitch();
    }

    private void handleIntent(Intent intent) {
        String key_id = "stadium";
        if(intent != null && intent.hasExtra(key_id)){
            String stadium_str = intent.getStringExtra(key_id);
            Gson gson = new Gson();
            stadium = gson.fromJson(stadium_str, Stadium.class);
        }
    }

    private void openBottomSheetContact() {
        BottomSheetHotline bottomSheetHotline = new BottomSheetHotline(this, stadium);
        bottomSheetHotline.show(getSupportFragmentManager(), bottomSheetHotline.getTag());
    }

    public void openBottomSheet(){
        List<Pitch> list = new ArrayList<>();
        if(stadium != null) list.addAll(stadium.getPitches());
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment(this, list, stadium);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
    private void getData(){
        if(stadium != null) slider.addAll(stadium.getImages());
    }

    private void initView(){
        sliderPitch = findViewById(R.id.sliderPitch);
        btn_direction = findViewById(R.id.btn_direction);

        toolbar = (Toolbar) findViewById(R.id.toolbar_detail_pitch);
        title_toolabr = (TextView) findViewById(R.id.title_toolbar);
        share = (ImageView) findViewById(R.id.share_toolbar);
        heart = (ImageView) findViewById(R.id.heart_toolbar);
        option = (ImageView) findViewById(R.id.option_toolbar);

        btn_booking = findViewById(R.id.btn_booking);
        btn_contact = findViewById(R.id.btn_contact);

        setSupportActionBar(toolbar);
        title_toolabr.setText(stadium != null ? stadium.getTitle() : "Chi tiết sân bóng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleShare();
            }
        });

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFavorite();
            }
        });

        btn_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleMap("32.323", "73.2342");
            }
        });

        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet();
            }
        });

        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheetContact();
            }
        });
    }

    private void handleShare() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, stadium.getTitle());
        shareIntent.putExtra(Intent.EXTRA_TEXT, stadium.getAddress());
        startActivity(Intent.createChooser(shareIntent, "Chia sẻ"));
    }

    private void handleFavorite() {

    }

    private void openGoogleMap(String latitude, String longitude) {
        Uri mapUri = Uri.parse("https://www.google.com/maps/search/" + latitude + "," + longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
        startActivity(intent);
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

    private void renderPitch() {
        TextView title = findViewById(R.id.detail_pitch_title);
        TextView address = findViewById(R.id.detail_pitch_address);
        TextView rating = findViewById(R.id.detail_pitch_rating);

        if(stadium != null){
            title.setText(stadium.getTitle());
            address.setText(stadium.getAddress());
            rating.setText(stadium.getAverage_rating());
        }

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