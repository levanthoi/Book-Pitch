package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.book_pitch.Adapters.FavouriteAdapter;
import com.example.book_pitch.Models.Favourite;
import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity implements FavouriteAdapter.FavouriteAdapterOnClickHandler{
    RecyclerView rcl_favourite;
    FirebaseFirestore db;
    ConstraintLayout emptyLayout;
    private FavouriteAdapter FavouriteAdapter;
    private ArrayList<Favourite> favouriteList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        getSupportActionBar().setTitle(R.string.favourite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emptyLayout = findViewById(R.id.emptyLayout);

        rcl_favourite = findViewById(R.id.rcl_favourite);
        rcl_favourite.setLayoutManager(new LinearLayoutManager(this));

        favouriteList = new ArrayList<>();
        FavouriteAdapter = new FavouriteAdapter(favouriteList, this);
        rcl_favourite.setAdapter(FavouriteAdapter);

        db = FirebaseFirestore.getInstance();
        loadDataFromFirestore();
    }
    private void loadDataFromFirestore() {
        db.collection("favourites")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                                favouriteList.clear();
                                for (QueryDocumentSnapshot document : querySnapshot) {
                                    if(document.exists()) {
                                        Favourite favourite = document.toObject(Favourite.class);
                                        favouriteList.add(favourite);
                                    }
                                }
                                FavouriteAdapter.notifyDataSetChanged();
                                if (favouriteList.isEmpty()) {
                                    emptyLayout.setVisibility(View.VISIBLE);
                                } else {
                                    emptyLayout.setVisibility(View.GONE);
                                }
                        } else {
                            Log.d("Firestore", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onClick(Favourite favouriteItem) {
        Intent intent = new Intent(FavouriteActivity.this, DetailPitchActivity.class);
        startActivity(intent);
    }
}