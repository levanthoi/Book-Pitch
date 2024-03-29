package com.example.book_pitch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.example.book_pitch.databinding.PopularViewBinding;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {


    private List<Stadium> stadiums;
    private Context ctx;
    private final PopularAdapterOnClickHandler clickHandler;

    public interface PopularAdapterOnClickHandler{
        void onClick(Stadium stadium);
    }
    public PopularAdapter(List<Stadium> stadiums, PopularAdapterOnClickHandler clickHandler) {
        this.stadiums = stadiums;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View view = layoutInflater.inflate(R.layout.popular_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Stadium stadium = stadiums.get(position);
        holder.txtTitle.setText(stadium.getTitle());
        holder.txtLocation.setText(stadium.getAddress());
        holder.txtPhone.setText(stadium.getPhone());
        holder.openTime.setText(stadium.getOpening_time() + "-" + stadium.getClosing_time());
    }

    @Override
    public int getItemCount() {
        return stadiums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle, txtLocation, txtPhone, openTime;
        public ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            openTime = itemView.findViewById(R.id.openTime);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Stadium s = stadiums.get(position);
                    clickHandler.onClick(s);
                }
            });
        }
    }
}
