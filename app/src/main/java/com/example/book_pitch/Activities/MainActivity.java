package com.example.book_pitch.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPopular, rclNearMe;
    private ListView lvNearMe;
    private ArrayList<Stadium> stadiums;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Window window = getWindow();
//        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

//        recyclerPopular = findViewById(R.id.rclPopular);
//        rclNearMe = findViewById(R.id.rclNearMe);
//        stadiums = new ArrayList<>();
//        for(int i=1;i<10;i++){
//            stadiums.add(new Stadium(i, "San bong "+i, "06:00", "18:00", "4,5", "0339083266", new Location(i,"Ha Noi "+ i)));
//        }
//        recyclerPopular.setAdapter(new PopularAdapter(stadiums));
//        recyclerPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerPopular.setHasFixedSize(true);
//
//        rclNearMe.setAdapter(new PopularAdapter(stadiums));
//        rclNearMe.setLayoutManager(new GridLayoutManager(this, 2));
//        rclNearMe.setHasFixedSize(true);
    }
}