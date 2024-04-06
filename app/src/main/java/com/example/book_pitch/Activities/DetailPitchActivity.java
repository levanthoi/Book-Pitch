package com.example.book_pitch.Activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
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
import com.example.book_pitch.Services.PitchService;
import com.example.book_pitch.Utils.AndroidUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailPitchActivity extends AppCompatActivity {
    private ViewPager2 sliderPitch;
    Button btn_booking, btn_contact, btn_direction;
    Toolbar toolbar;
    TextView title_toolabr;
    ImageView share, heart, option;
    private Handler slideHandler = new Handler();
    List<String> slider = new ArrayList<>();
    Stadium stadium;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pitch);

        getWindow().setStatusBarColor(Color.parseColor("#198754"));

        handleIntent(getIntent());
        firestore = FirebaseFirestore.getInstance();
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
//        PitchService pitchService = new PitchService();
        if(stadium != null) {
            firestore.collection("pitches")
                    .whereEqualTo("stadium_id", "0sRWgWvgDk3d3N8c2Tch")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if(document.exists()){
                                        Pitch p = document.toObject(Pitch.class);
                                        list.add(p);
                                    }

                                }
                            }
                        }
                    });
        }
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

//       HANDLE HEART
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isHeartOn = heart.getTag() != null && (boolean) heart.getTag();
                FirebaseUser mCurrent = FirebaseAuth.getInstance().getCurrentUser();
                if(mCurrent != null) {
                    String userId = mCurrent.getUid();
                    if (isHeartOn) {
                        heart.setImageResource(R.drawable.heart_outline);
                        heart.setTag(false);
                        // Thực hiện các hành động khi heart bị tắt
                        if(mCurrent != null) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            Map<String, Object> updates = new HashMap<>();
                            updates.put(stadium.getId(), FieldValue.delete());

                            db.collection("favourites").document(userId)
                                    .update(updates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(DetailPitchActivity.this, "Đã xóa khỏi danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(DetailPitchActivity.this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        heart.setImageResource(R.drawable.heart_outline_red);
                        heart.setTag(true);
                        // Thực hiện các hành động khi heart được bật

                        if(mCurrent != null) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();

                            Map<String, Object> pitchData = new HashMap<>();
//                        pitchData.put("pitchId", stadium.getId());
//                        pitchData.put("pitchTitle", stadium.getTitle());
//                        pitchData.put("pitchAddress", stadium.getAddress());
//                        pitchData.put("pitchPhone", stadium.getPhone());
                            pitchData.put(stadium.getId(), true);
                            db.collection("favourites").document(userId)
                                    .set(pitchData, SetOptions.merge())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(DetailPitchActivity.this, "Đã thêm vào danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                } else {
                    Toast.makeText(DetailPitchActivity.this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        handleFavorite();

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
        FirebaseUser mCurrent = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(mCurrent != null) {
            String userId = mCurrent.getUid();
            db.collection("favourites").document(userId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    if (document.getData().containsKey(stadium.getId())) {
                                        heart.setImageResource(R.drawable.heart_outline_red);
                                    } else {
                                        heart.setImageResource(R.drawable.heart_outline);
                                    }
                                }
                            }
                        }
                    });
        }
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