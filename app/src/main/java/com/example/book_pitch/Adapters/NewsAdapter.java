
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
import com.example.book_pitch.Models.New;
import com.example.book_pitch.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<New> News;
    private OnNewsItemClickHandler clickHandler;
    public interface OnNewsItemClickHandler {
        void onClick(New newItem);
    }
    public NewsAdapter(ArrayList<New> News, OnNewsItemClickHandler clickHandler) {
        this.News = News;
        this.clickHandler = clickHandler;
    }
    public void setNewsItems(ArrayList<New> newsItems) {
        this.News = newsItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_new, parent, false);
        return new NewsAdapter.ViewHolder(view);
    }

    public void filterNewsItems(String keyword) {
        keyword = keyword.toLowerCase();
        ArrayList<New> filteredList = new ArrayList<>();
        for (New newsItem : News) {
            if (newsItem.getTitle().toLowerCase().contains(keyword)) {
                filteredList.add(newsItem);
            }
        }
        this.News = filteredList;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        New newsItem = News.get(position);
        holder.bind(newsItem);
    }

    @Override
    public int getItemCount() {
        return News == null ? 0 : News.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvDescription;
        TextView tvpubDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.img_new);
            tvTitle = itemView.findViewById(R.id.txtTitle_new);
            tvDescription = itemView.findViewById(R.id.txtDescription_new);
            tvpubDate = itemView.findViewById(R.id.txtPubDate_new);
            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    New n = News.get(position);
                    clickHandler.onClick(n);
                }
            });
        }

        public void bind(New newsItem) {
            tvTitle.setText(newsItem.getTitle());
            tvDescription.setText(newsItem.getDescription());
            tvpubDate.setText(newsItem.getpubDate());
            Glide.with(context).load(newsItem.getImage()).into(ivImage);
        }
    }
}
