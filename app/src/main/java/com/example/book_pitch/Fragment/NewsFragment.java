package com.example.book_pitch.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.book_pitch.Models.News_item;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.GetNews;

import java.util.List;

public class NewsFragment extends Fragment {

    public String link = "https://vnexpress.net/rss/the-thao.rss";
    RecyclerView rcvNews;
    private Context ctx;
    private List<News_item> mListNews;

    public NewsFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        rcvNews = view.findViewById(R.id.rv_blogs);
        /*LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);*/
        GetNews getNews = new GetNews(rcvNews,link, getContext());
       /* rcvNews.setLayoutManager(manager);
        rcvNews.setAdapter();*/
      /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        rcvNews.setLayoutManager(linearLayoutManager);
        NewsAdapter newsAdapter = new NewsAdapter(cxt, getNewsList());
        rcvNews.setAdapter(newsAdapter);*/

        // Inflate the layout for this fragment
        return view;
    }
    /*private List<News_item> getNewsList(){
        List<News_item> list = new ArrayList<>();

        for(int i=1; i<=10; i++){
            News_item news = new News_item(i+"Top 5 lịch sử thú vị về Euro", "https://s.yimg.com/fz/api/res/1.2/JmKnAYUB3x_xe4e1_USlXQ--~C/YXBwaWQ9c3JjaGRkO2ZpPWZpbGw7aD0zODQ7cT04MDt3PTUxMg--/https://www.bing.com/th?id=OVFT.qTPdK1nejgowIFQaD6485C&pid=News&w=700&h=365&c=14&qlt=90&dpr=2", "1.Sô bàn thắng nhiều nhất ghi được trong một trận đấu [...]", "6" );
            list.add(news);
        }
        return list;

    }*/

   /* public void My(){
        MyAsyncTask_new task = new MyAsyncTask_new(rcvNews,link,getContext());
        task.execute();
    }*/

}

