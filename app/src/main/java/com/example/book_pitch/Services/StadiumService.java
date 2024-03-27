package com.example.book_pitch.Services;

import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.Utils.FirebaseUtil;

import java.util.List;

public class StadiumService {
//    private FirebaseUtil.OnDataLoadedListener<Stadium> onDataLoadedListener;
//    public StadiumService(FirebaseUtil.OnDataLoadedListener<Stadium> onDataLoadedListener) {
//        this.onDataLoadedListener = onDataLoadedListener;
//    }

    public static void getAll(FirebaseUtil.OnDataLoadedListener<Stadium> onDataLoadedListener) {
        FirebaseUtil.getAll("stadiums", Stadium.class, new FirebaseUtil.OnDataLoadedListener<Stadium>() {
            @Override
            public void onDataLoaded(List<Stadium> data) {
                onDataLoadedListener.onDataLoaded(data);
            }

            @Override
            public void onError(String errorMessage) {
                onDataLoadedListener.onError(errorMessage);
            }
        });
    }

    public static void getOne() {
//        FirebaseUtil.getOne()
    }
}
