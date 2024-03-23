package com.example.book_pitch.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.book_pitch.Adapters.NotificationAdapter;
import com.example.book_pitch.Models.Notification;
import com.example.book_pitch.R;
import com.example.book_pitch.databinding.FragmentNotificationBinding;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding mFragmentNotificationBinding;

    private View mView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentNotificationBinding = FragmentNotificationBinding.inflate(inflater, container, false);
        mFragmentNotificationBinding.textView9.setText("Thông báo");
        mView = mFragmentNotificationBinding.getRoot();

        RecyclerView rcvNoti = mFragmentNotificationBinding.rvNoti;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext());
        rcvNoti.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mView.getContext(), DividerItemDecoration.VERTICAL);
        rcvNoti.addItemDecoration(dividerItemDecoration);

        NotificationAdapter notiAdapter = new NotificationAdapter(getNotiList());
        rcvNoti.setAdapter(notiAdapter);

        return mView;
    }

    private List<Notification> getNotiList(){
        List<Notification> list = new ArrayList<>();
        list.add(new Notification("thông báo 1"));
        list.add(new Notification("thông báo 2"));
        list.add(new Notification("thông báo 3"));
        list.add(new Notification("thông báo 4"));
        list.add(new Notification("thông báo 5"));
        return list;

    }
}