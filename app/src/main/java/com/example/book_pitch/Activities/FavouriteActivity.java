package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.book_pitch.Adapters.FavouriteAdapter;
import com.example.book_pitch.Models.Favourite;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FavouriteActivity extends AppCompatActivity implements FavouriteAdapter.FavouriteAdapterOnClickHandler{
    RecyclerView rcl_favourite;
    FirebaseFirestore db;
    ConstraintLayout emptyLayout;
    private FavouriteAdapter FavouriteAdapter;
    private ArrayList<Favourite> favouriteList;
    Stadium stadium;
    TextView tvTitle, tvAddress,tvPhoneNumber,tvRating;
    ShapeableImageView avatar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.favourite);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        emptyLayout = findViewById(R.id.emptyLayout);

        rcl_favourite = findViewById(R.id.rcl_favourite);
        rcl_favourite.setLayoutManager(new LinearLayoutManager(this));

        favouriteList = new ArrayList<>();
        FavouriteAdapter = new FavouriteAdapter(favouriteList, this);
        rcl_favourite.setAdapter(FavouriteAdapter);

        tvTitle = findViewById(R.id.tvTitle);
        tvAddress = findViewById(R.id.tvAddress);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvRating = findViewById(R.id.tvRating);
        avatar = findViewById(R.id.avatar);

        db = FirebaseFirestore.getInstance();
        loadDataFromFirestore();
        renderData();
    }

    private void renderData() {
        if(stadium != null) {
            tvTitle.setText(stadium.getTitle());
            tvAddress.setText(stadium.getAddress());
            tvPhoneNumber.setText(stadium.getPhone());
            tvRating.setText(stadium.getAverage_rating());
            Glide.with(avatar)
                    .load(stadium.getImages());
        }
    }

    private void loadDataFromFirestore() {
        FirebaseUser mCurrent = FirebaseAuth.getInstance().getCurrentUser();
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
                                    favouriteList.clear();
                                    Map<String, Object> data = document.getData();
                                    if (data != null) {
                                        for (Map.Entry<String, Object> entry : data.entrySet()) {
                                            String key = entry.getKey();
                                            Favourite favourite = new Favourite(key);
                                            favouriteList.add(favourite);
                                            db.collection("stadiums").document(key)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {

                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                    if (favouriteList.isEmpty()) {
                                        emptyLayout.setVisibility(View.VISIBLE);
                                    } else {
                                        emptyLayout.setVisibility(View.GONE);
                                    }
                                }
                            } else {
                                Log.d("Firestore", "Error getting document: ", task.getException());
                            }
                        }
                    });
        }
    }

    @Override
    public void onClick(Favourite favouriteItem) {
        Intent intent = new Intent(FavouriteActivity.this, DetailPitchActivity.class);
        startActivity(intent);
    }

}