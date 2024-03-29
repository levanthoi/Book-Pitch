package com.example.book_pitch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Models.Favourite;
import com.example.book_pitch.R;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    private ArrayList<Favourite> favouriteList;

    private Context ctx;
    private final FavouriteAdapter.FavouriteAdapterOnClickHandler clickHandler;

    public interface FavouriteAdapterOnClickHandler {
        void onClick(Favourite favouriteItem);
    }

    public FavouriteAdapter(ArrayList<Favourite> favouriteList, FavouriteAdapter.FavouriteAdapterOnClickHandler clickHandler) {
        this.favouriteList = favouriteList;
        this.clickHandler = clickHandler;
    }
    public void setData(ArrayList<Favourite> data) {
        this.favouriteList.clear();
        this.favouriteList.addAll(data);
        notifyDataSetChanged();
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
        Favourite favouriteItem = favouriteList.get(position);
        holder.bind(favouriteItem);
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Khai báo các view trong item layout
        // ...
        private TextView tvTitle, tvAddress, tvPhoneNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);

            itemView.setOnClickListener(this);
        }

        public void bind(Favourite favouriteItem) {
            tvTitle.setText(favouriteItem.getTitle());
            tvAddress.setText(favouriteItem.getAddress());
            tvPhoneNumber.setText(favouriteItem.getPhone());
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Favourite favouriteItem = favouriteList.get(adapterPosition);
            clickHandler.onClick(favouriteItem);
        }
    }
}
