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
import com.example.book_pitch.Models.Notifi;
import com.example.book_pitch.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final List<Notifi> mListNoti;

    private Context ctx;

    public NotificationAdapter(List<Notifi> mListNoti) {
        this.mListNoti = mListNoti;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_noti, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notifi noti = mListNoti.get(position);
        holder.txtTitle_noti.setText(noti.getTitle());
        holder.txtContent_noti.setText(noti.getContent());
        holder.image.setBackgroundResource(R.drawable.logo);
    }

    @Override
    public int getItemCount() {
        return mListNoti == null ? 0 : mListNoti.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle_noti;
        private TextView txtContent_noti;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle_noti = itemView.findViewById(R.id.txtTitle_noti);
            txtContent_noti = itemView.findViewById(R.id.txtContent_noti);
            image = itemView.findViewById(R.id.image);
        }
    }

}
