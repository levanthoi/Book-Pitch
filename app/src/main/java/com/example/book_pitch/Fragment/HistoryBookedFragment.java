package com.example.book_pitch.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.book_pitch.Adapters.HistoryBookedAdapter;
import com.example.book_pitch.Adapters.PopularAdapter;
import com.example.book_pitch.Models.Bill;
import com.example.book_pitch.Models.Location;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;

import java.util.ArrayList;

public class HistoryBookedFragment extends Fragment {

    private RecyclerView recycleHistoryBooked;
    private ArrayList<Bill> bills;

    public HistoryBookedFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_booked, container, false);
    }
    private void init(View view) {
        recycleHistoryBooked = view.findViewById(R.id.rcl_history_booked);
        bills = new ArrayList<>();
//        recycleHistoryBooked.setAdapter(new HistoryBookedAdapter(bills, this));
    }
}