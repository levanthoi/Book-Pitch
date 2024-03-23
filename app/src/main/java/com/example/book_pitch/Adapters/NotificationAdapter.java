package com.example.book_pitch.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Models.Notification;
import com.example.book_pitch.databinding.ItemNotiBinding;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotiViewHolder> {

    private final List<Notification> mListNoti;

    public NotificationAdapter(List<Notification> mListNoti) {
        this.mListNoti = mListNoti;
    }

    @NonNull
    @Override
    public NotiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotiBinding notiBinding = ItemNotiBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NotiViewHolder(notiBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotiViewHolder holder, int position) {
        Notification noti = mListNoti.get(position);
        if(noti == null){
            return;
        }
    }

    @Override
    public int getItemCount() {
        if(mListNoti != null){
            return mListNoti.size();
        }
        return 0;
    }

    public static  class NotiViewHolder extends RecyclerView.ViewHolder {

        private ItemNotiBinding notiBinding;

        public NotiViewHolder(@NonNull ItemNotiBinding notiBinding) {
            super(notiBinding.getRoot());
            this.notiBinding = notiBinding;
        }
    }

}
