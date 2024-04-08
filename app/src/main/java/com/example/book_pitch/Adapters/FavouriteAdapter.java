package com.example.book_pitch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    private ArrayList<Stadium> favouriteList;

    private Context ctx;
    private final FavouriteAdapterOnClickHandler clickHandler;

    public interface FavouriteAdapterOnClickHandler {
        void onClick(Stadium favouriteItem);
    }

    public FavouriteAdapter(ArrayList<Stadium> favouriteList, FavouriteAdapterOnClickHandler clickHandler) {
        this.favouriteList = favouriteList;
        this.clickHandler = clickHandler;
    }
    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_favourite, parent, false);
        return new FavouriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
        Stadium favouriteItem = favouriteList.get(position);
        holder.bind(favouriteItem);
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView tvTitle, tvAddress, tvPhoneNumber, tvRating;
        private ShapeableImageView avatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            tvRating = itemView.findViewById(R.id.tvRating);
            avatar = itemView.findViewById(R.id.avatar);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Stadium f = favouriteList.get(position);
                    clickHandler.onClick(f);
                }
            });
        }

        public void bind(Stadium favouriteItem) {
            tvTitle.setText(favouriteItem.getTitle());
            tvAddress.setText(favouriteItem.getAddress());
            tvPhoneNumber.setText(favouriteItem.getPhone());
            tvRating.setText(favouriteItem.getAverage_rating());
            Glide.with(itemView.getContext())
                    .load(favouriteItem.getAvatar())
                    .into(avatar);
        }
    }
}
