package com.example.book_pitch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Models.Notifi;
import com.example.book_pitch.Models.Review;
import com.example.book_pitch.R;

import java.util.List;

public class DetailPitchAdapter extends RecyclerView.Adapter<DetailPitchAdapter.ViewHolder> {
    private final List<Review> reviewList;
    private Context ctx;

    public DetailPitchAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_review, parent, false);
        return new DetailPitchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.rating.setText(review.getRating());
        holder.comment.setText(review.getComment());
        holder.star.setBackgroundResource(R.drawable.star_solid);
    }

    @Override
    public int getItemCount() {
        return reviewList == null ? 0 : reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView rating;
        private TextView comment;
        private ImageView star;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rating = itemView.findViewById(R.id.rating);
            comment = itemView.findViewById(R.id.comment);
            star = itemView.findViewById(R.id.star);
        }
    }
}
