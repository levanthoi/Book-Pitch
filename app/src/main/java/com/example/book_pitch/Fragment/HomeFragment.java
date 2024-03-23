package com.example.book_pitch.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.book_pitch.Activities.DetailPitchActivity;
import com.example.book_pitch.Adapters.PopularAdapter;
import com.example.book_pitch.Models.Location;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements PopularAdapter.PopularAdapterOnClickHandler {
    private RecyclerView recyclerPopular, rclNearMe;
    private ListView lvNearMe;
    private ArrayList<Stadium> stadiums;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        recyclerPopular = view.findViewById(R.id.rclPopular);
        rclNearMe = view.findViewById(R.id.rclNearMe);
        stadiums = new ArrayList<>();
        for(int i=1;i<10;i++){
            stadiums.add(new Stadium(i, "San bong "+i, "06:00", "18:00", "4,5", "0339083266", new Location(i,"Ha Noi "+ i)));
        }
        recyclerPopular.setAdapter(new PopularAdapter(stadiums, this));
        recyclerPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerPopular.setHasFixedSize(true);

        rclNearMe.setAdapter(new PopularAdapter(stadiums, this));
        rclNearMe.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rclNearMe.setHasFixedSize(true);
        rclNearMe.setNestedScrollingEnabled(false);
    }

    @Override
    public void onClick(Stadium stadium) {
        Intent intent = new Intent(getActivity(), DetailPitchActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}