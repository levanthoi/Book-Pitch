package com.example.book_pitch.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.book_pitch.Models.News;
import com.example.book_pitch.databinding.RowBinding;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final List<News> mListNews;


    public NewsAdapter(List<News> mListNews) {
        this.mListNews = mListNews;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowBinding rowBinding = RowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new NewsViewHolder(rowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = mListNews.get(position);
        if(news == null){
            return;
        }

    }

    @Override
    public int getItemCount() {
        if(mListNews != null){
            return mListNews.size();
        }
        return 0;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        private final RowBinding rowBinding;

        public NewsViewHolder(@NonNull RowBinding rowBinding) {
            super(rowBinding.getRoot());
            this.rowBinding = rowBinding;
        }
    }

}
