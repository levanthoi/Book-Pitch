package com.example.book_pitch.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.book_pitch.Adapters.NewsAdapter;
import com.example.book_pitch.Models.News;
import com.example.book_pitch.R;
import com.example.book_pitch.databinding.FragmentNewsBinding;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding mFragmentNewsBinding;
    private View mView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentNewsBinding = FragmentNewsBinding.inflate(inflater, container, false);
        mFragmentNewsBinding.textView.setText("Tin tức");

        mView = mFragmentNewsBinding.getRoot();

        RecyclerView rcvNews = mFragmentNewsBinding.rvBlogs;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext());
        rcvNews.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mView.getContext(), DividerItemDecoration.VERTICAL);
        rcvNews.addItemDecoration(dividerItemDecoration);

        NewsAdapter newsAdapter = new NewsAdapter(getNewsList());
        rcvNews.setAdapter(newsAdapter);

        // Inflate the layout for this fragment
        return mView;
    }
    private List<News> getNewsList(){
        List<News> list = new ArrayList<>();

        for(int i=1; i<=10; i++){
            News news = new News(i+"Top 5 lịch sử thú vị về Euro", "https://s.yimg.com/fz/api/res/1.2/JmKnAYUB3x_xe4e1_USlXQ--~C/YXBwaWQ9c3JjaGRkO2ZpPWZpbGw7aD0zODQ7cT04MDt3PTUxMg--/https://www.bing.com/th?id=OVFT.qTPdK1nejgowIFQaD6485C&pid=News&w=700&h=365&c=14&qlt=90&dpr=2", "1.Sô bàn thắng nhiều nhất ghi được trong một trận đấu [...]", "6" );
            list.add(news);
        }
        return list;

    }

}

