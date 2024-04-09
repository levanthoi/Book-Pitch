
package com.example.book_pitch.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book_pitch.Adapters.NewsAdapter;
import com.example.book_pitch.Models.New;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.GetNews;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements NewsAdapter.OnNewsItemClickHandler{

    RecyclerView rcvNews;
    private NewsAdapter newsAdapter;
    private ArrayList<New> news;
    EditText search_news;
    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        rcvNews = view.findViewById(R.id.rv_blogs);
        rcvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        news = new ArrayList<>();
        newsAdapter = new NewsAdapter(news, this);
        rcvNews.setAdapter(newsAdapter);
        search_news = view.findViewById(R.id.search_news);
        setupSearchListener();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchNews();
    }

    private void fetchNews() {
        GetNews.getNews(new GetNews.NewsCallback() {
            @Override
            public void onSuccess(ArrayList<New> newsItems) {
                if (newsItems != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (newsItems != null) {
                                newsAdapter.setNewsItems(newsItems);
                            }
                        }
                    });
                }
            }
            @Override
            public void onError(String errorMessage) {
                // Xử lý khi có lỗi xảy ra trong quá trình lấy dữ liệu
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(New newItem) {
        String url = newItem.getLink();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void handleSearch(String keyword) {
        if (newsAdapter != null) {
            newsAdapter.filterNewsItems(keyword);
        }
    }

    private void setupSearchListener() {

        search_news.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim().toLowerCase();
                if (keyword.isEmpty()) {
                    fetchNews();
                } else {
                    handleSearch(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
