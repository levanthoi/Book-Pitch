package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;

import com.example.book_pitch.Adapters.FavouriteAdapter;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class FavouriteActivity extends AppCompatActivity implements FavouriteAdapter.FavouriteAdapterOnClickHandler{
    RecyclerView rcl_favourite;
    FirebaseFirestore db;
    ConstraintLayout emptyLayout;
    private FavouriteAdapter FavouriteAdapter;
    private ArrayList<Stadium> favouriteList;
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
        favouriteList = new ArrayList<>();
        FavouriteAdapter = new FavouriteAdapter(favouriteList, this);

            rcl_favourite.setLayoutManager(new GridLayoutManager(this, 1));
        rcl_favourite.setAdapter(FavouriteAdapter);
        db = FirebaseFirestore.getInstance();
        loadDataFromFirestore();

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
                                            renderData(key);
                                        }
                                    }
                                }
                            } else {
                                Log.d("Firestore", "Error getting document: ", task.getException());
                            }
                        }
                    });
        }
    }
    private void renderData(String stadiumId) {
            db.collection("stadiums").document(stadiumId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot stadiumDocument = task.getResult();
                                if (stadiumDocument.exists()) {
                                    String stadiumId = stadiumDocument.getString("id");
                                    String stadiumName = stadiumDocument.getString("title");
                                    String stadiumAddress = stadiumDocument.getString("address");
                                    String stadiumPhone = stadiumDocument.getString("phone");
                                    String stadiumRating = stadiumDocument.getString("average_rating");
                                    String stadiumAvatar = stadiumDocument.getString("avatar");

                                    Stadium stadium = new Stadium(stadiumId,stadiumName, stadiumRating, stadiumPhone, stadiumAddress, stadiumAvatar);
                                    if(stadium != null) {
                                        favouriteList.add(stadium);
                                        FavouriteAdapter.notifyDataSetChanged();
                                    }
                                }
                                if (favouriteList.isEmpty()) {
                                    emptyLayout.setVisibility(View.VISIBLE);
                                } else {
                                    emptyLayout.setVisibility(View.GONE);
                                }
                            }
                        }
                    });
    }
    @Override
    public void onClick(Stadium favouriteItem) {
        db.collection("stadiums").document(favouriteItem.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()) {
                                Stadium stadium = document.toObject(Stadium.class);
                                if(stadium != null) {
                                    Gson gson = new Gson();
                                    String stadiumJson = gson.toJson(stadium);
                                    Intent intent = new Intent(FavouriteActivity.this, DetailPitchActivity.class);
                                    intent.putExtra("stadium", stadiumJson);
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                });
    }

}