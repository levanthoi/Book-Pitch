package com.example.book_pitch.Fragment;

import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.book_pitch.Adapters.NewsAdapter;
import com.example.book_pitch.Models.News;
import com.example.book_pitch.R;
import com.example.book_pitch.databinding.FragmentNewsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    RecyclerView rcvNews;

    FirebaseFirestore fireStore;
    NewsAdapter newsAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);


        rcvNews = view.findViewById(R.id.rv_blogs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        rcvNews.setLayoutManager(linearLayoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
//        rcvNews.addItemDecoration(dividerItemDecoration);

        newsAdapter = new NewsAdapter(getNewsList());
        rcvNews.setAdapter(newsAdapter);

        // Inflate the layout for this fragment
        return view;
    }
    private List<News> getNewsList(){
        List<News> list = new ArrayList<>();

        fireStore = FirebaseFirestore.getInstance();

        fireStore.collection("news")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                News news = document.toObject(News.class);
                                list.add(news);
                                newsAdapter.notifyDataSetChanged();
                                /*Log.d(Tag, document.getId() + " => " + p);*/
                            }
                        } else {
//                            Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi nào đó.", Toast.LENGTH_SHORT).show();
                        }
//                        progressBar.setVisibility(View.GONE);
                    }
                });
        return list;

    }



}

