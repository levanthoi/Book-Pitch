package com.example.book_pitch.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.book_pitch.Activities.DetailPitchActivity;
import com.example.book_pitch.Adapters.HistoryBookedAdapter;
import com.example.book_pitch.Models.Bill;
import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HistoryBookedFragment extends Fragment implements HistoryBookedAdapter.HistoryBookedAdapterOnClickHandler{

    RecyclerView rcl_history_booked;
    ArrayList<Bill> bills;
    ConstraintLayout emptyLayout;
    FirebaseFirestore db;

    HistoryBookedAdapter HistoryBookedAdapter;
    public HistoryBookedFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_booked, container, false);
        init(view);
        return view;
    }
    private void init(View view) {
        bills = new ArrayList<>();
        emptyLayout = view.findViewById(R.id.emptyLayout);
        rcl_history_booked = view.findViewById(R.id.rcl_history_booked);
        rcl_history_booked.setLayoutManager(new LinearLayoutManager(getContext()));

        bills = new ArrayList<>();
        HistoryBookedAdapter = new HistoryBookedAdapter(bills, this);
        rcl_history_booked.setAdapter(HistoryBookedAdapter);

        db = FirebaseFirestore.getInstance();
        loadDataFromFirestore();
    }
    private void loadDataFromFirestore() {
        db.collection("bills")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            bills.clear();
                                for (QueryDocumentSnapshot document : querySnapshot) {
                                    if(document.exists()) {
                                        Bill bill = document.toObject(Bill.class);
                                        bills.add(bill);
                                    }
                                }
                            HistoryBookedAdapter.notifyDataSetChanged();
                                if (bills.isEmpty()) {
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
    public void onClick(Bill bill) {
        Intent intent = new Intent(getActivity(), DetailPitchActivity.class);
        startActivity(intent);
    }
}