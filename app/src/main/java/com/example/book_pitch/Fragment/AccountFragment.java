package com.example.book_pitch.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.book_pitch.Activities.BookedAndHistoryActivity;
import com.example.book_pitch.R;

public class AccountFragment extends Fragment {
    TextView history_book;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        history_book = view.findViewById(R.id.history_book);
        history_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BookedAndHistoryActivity.class);
                startActivity(i);
            }
        });
    }
}