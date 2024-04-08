package com.example.book_pitch.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.book_pitch.Activities.DetailPitchActivity;
import com.example.book_pitch.Activities.PaymentSuccessActivity;
import com.example.book_pitch.Adapters.PitchBookedAdapter;
import com.example.book_pitch.Models.Bill;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.AndroidUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AllFragment extends Fragment implements PitchBookedAdapter.PitchBookedAdapterOnClickHandler {
    private PitchBookedAdapter pitchBookedAdapter;
    private List<Bill> bills;
    private FirebaseFirestore firestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        firestore = FirebaseFirestore.getInstance();

        getAllBills();
        init(view);
        return view;
    }

    private void init(View view) {
        RecyclerView rcv_all_tab = view.findViewById(R.id.rcv_all_tab);

        bills = new ArrayList<>();
        rcv_all_tab.setLayoutManager(new LinearLayoutManager(getContext()));
        pitchBookedAdapter = new PitchBookedAdapter(bills, this);
        rcv_all_tab.setAdapter(pitchBookedAdapter);
    }

    private void getAllBills() {
        firestore.collection("bills").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.exists()) {
                            bills.add(document.toObject(Bill.class));
                        }
                    }
                    pitchBookedAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(Bill bill) {

        bill.stadium(new Bill.OnStadiumFetchListener() {
            @Override
            public void onStadiumFetch(Stadium stadium) {
                Intent intent = new Intent(getActivity(), DetailPitchActivity.class);
                intent.putExtra("stadium", new Gson().toJson(stadium));
                startActivity(intent);
            }
        });


    }
}