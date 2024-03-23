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

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {


    private ArrayList<Stadium> stadiums;
    private Context ctx;
    private final PopularAdapterOnClickHandler clickHandler;

    public interface PopularAdapterOnClickHandler{
        void onClick(Stadium stadium);
    }
    public PopularAdapter(ArrayList<Stadium> stadiums, PopularAdapterOnClickHandler clickHandler) {
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
        holder.txtTitle.setText(stadiums.get(position).getTitle());
        holder.txtLocation.setText(stadiums.get(position).getLocation().getArea());
        holder.txtPhone.setText(stadiums.get(position).getPhone());
        holder.openTime.setText(stadiums.get(position).getPhone());
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
