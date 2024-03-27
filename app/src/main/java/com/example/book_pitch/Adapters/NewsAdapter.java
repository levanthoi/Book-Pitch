package com.example.book_pitch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.book_pitch.Models.News;
import com.example.book_pitch.R;
import com.example.book_pitch.databinding.ItemNewBinding;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final List<News> mListNews;

    private Context ctx;


    public NewsAdapter(List<News> mListNews) {
        this.mListNews = mListNews;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*ItemNewBinding itemNewBinding = ItemNewBinding.inflate(R.layout., parent, false);*/
        ctx = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View view = layoutInflater.inflate(R.layout.item_new, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = mListNews.get(position);
        if(news == null){
            return;
        }
        holder.txtDate_new.setText("Tháng " + news.getDate() );
        holder.txtTitle_new.setText(news.getTitle());
        holder.txtDescription_new.setText(news.getDescription());
        /*holder.txtShare_new.setText("162 Shared");*/
        Glide.with(ctx)
                .load(news.getImage())
                .into(holder.img_new);

    }

    @Override
    public int getItemCount() {
        if(mListNews != null){
            return mListNews.size();
        }
        return 0;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDate_new;
        private TextView txtTitle_new;
        private TextView txtDescription_new;
        private TextView txtShare_new;
        private ImageView img_new;
        private CardView Card_new;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate_new =itemView.findViewById(R.id.txtDate_new);
            txtTitle_new =itemView.findViewById(R.id.txtTitle_new);
            txtDescription_new =itemView.findViewById(R.id.txtDescription_new);
            txtShare_new =itemView.findViewById(R.id.txtShare_new);
            img_new =itemView.findViewById(R.id.img_new);
            Card_new = itemView.findViewById(R.id.Card_new);

        }
    }

}
