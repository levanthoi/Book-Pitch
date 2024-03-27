package com.example.book_pitch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.book_pitch.Models.Notification;
import com.example.book_pitch.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotiViewHolder> {

    private final List<Notification> mListNoti;

    private Context ctx;

    public NotificationAdapter(List<Notification> mListNoti) {
        this.mListNoti = mListNoti;
    }

    @NonNull
    @Override
    public NotiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View view = layoutInflater.inflate(R.layout.item_noti, parent, false);
        return new NotiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotiViewHolder holder, int position) {
        Notification noti = mListNoti.get(position);
        if(noti == null){
            return;
        }
        holder.txtTitle_noti.setText(noti.getTitle());
        holder.txtContent_noti.setText(noti.getContent());
        holder.txtTime_noti.setText(noti.getTime());
        holder.txtBacham_noti.setText("...");
        Glide.with(ctx)
                .load(noti.getImage())
                .into(holder.image_noti);
    }

    @Override
    public int getItemCount() {
        if(mListNoti != null){
            return mListNoti.size();
        }
        return 0;
    }

    public static  class NotiViewHolder extends RecyclerView.ViewHolder {

        private ImageView image_noti;
        private TextView txtTitle_noti;
        private TextView txtContent_noti;
        private TextView txtTime_noti;
        private TextView txtBacham_noti;

        public NotiViewHolder(@NonNull View itemView) {
            super(itemView);
            image_noti = itemView.findViewById(R.id.image_noti);
            txtTitle_noti = itemView.findViewById(R.id.txtTitle_noti);
            txtContent_noti = itemView.findViewById(R.id.txtContent_noti);
            txtTime_noti = itemView.findViewById(R.id.txtTime_noti);
            txtBacham_noti = itemView.findViewById(R.id.txtBacham_noti);


        }
    }

}
