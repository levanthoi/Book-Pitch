package com.example.book_pitch.Utils;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Adapters.NewsAdapter;
import com.example.book_pitch.Models.MySaxParser_new;
import com.example.book_pitch.Models.News_item;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetNews {

    private String link;
    RecyclerView rcvNews;
    private List<News_item> list;

    private NewsAdapter adapter;
    private Context cxt;

    public interface NewsCallback {
        void onSuccess(List<News_item> newsItems);

        void onError(String errorMessage);
    }
    public GetNews(RecyclerView rcvNews,String link, Context cxt){
        this.rcvNews = rcvNews;
        this.link= link;
        this.cxt = cxt;
    }

    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    public void fetchNews(NewsCallback callback) {
        executorService.submit(() -> {
            List<News_item> list = new ArrayList<>();
            try {
                // Thực hiện các thao tác lấy dữ liệu từ RSS Feed ở đây
                // Ví dụ:
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                list = MySaxParser_new.xmlParser(is);
                // Xử lý dữ liệu RSS và tạo danh sách newsItems
            } catch (Exception e) {
                // Nếu có lỗi, trả về null
                callback.onError("Failed to fetch news");
                return;
            }
            callback.onSuccess(list);
        });
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
