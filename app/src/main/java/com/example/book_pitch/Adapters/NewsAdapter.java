package com.example.book_pitch.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Activities.Detail_newActivity;
import com.example.book_pitch.Models.News_item;
import com.example.book_pitch.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<News_item> mListNews;
    private Context ctx;

    public NewsAdapter(Context cxt, List<News_item> mListNews) {
        this.ctx = ctx;
        this.mListNews = mListNews;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*ctx = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News_item news = mListNews.get(position);
        if(news == null){
            return;
        }
        holder.txtTitle_new.setText(news.getTitle());
        holder.txtDescription_new.setText(news.getDescription());
        holder.txtPubDate_new.setText(news.getPubdate() );
        holder.Reltaive_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, Detail_newActivity.class);
                String link = news.getLink();
                intent.putExtra("link", link);
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mListNews != null){
            return mListNews.size();
        }
        return 0;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView txtPubDate_new;
        private TextView txtTitle_new;
        private TextView txtDescription_new;
        private ImageView img_new;
        private RelativeLayout Reltaive_new;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPubDate_new =itemView.findViewById(R.id.txtPubDate_new);
            txtTitle_new =itemView.findViewById(R.id.txtTitle_new);
            txtDescription_new =itemView.findViewById(R.id.txtDescription_new);
            img_new =itemView.findViewById(R.id.img_new);
            Reltaive_new = itemView.findViewById(R.id.Reltaive_new);

        }
    }

}
