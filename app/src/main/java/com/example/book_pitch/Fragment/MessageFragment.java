package com.example.book_pitch.Fragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.book_pitch.Activities.MessageItemActivity;
import com.example.book_pitch.Adapters.MessageGroupAdapter;
import com.example.book_pitch.Models.MessageGroup;
import com.example.book_pitch.R;
import com.google.gson.Gson;

import java.util.ArrayList;
public class MessageFragment extends Fragment implements MessageGroupAdapter.MessageGroupAdapterOnClickHandler {
    private RecyclerView messageRecyclerView;
    private ArrayList<MessageGroup> messages;

    public MessageFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        init(view);
        return view;
    }
    private void init(View view) {
        messageRecyclerView = view.findViewById(R.id.messageRecyclerView);

        messages = new ArrayList<>();
        for(int i=1;i<10;i++){
            messages.add(new MessageGroup("Lê Như Nam","0965446137","Làm bài đê anh em",1));
        }
        messageRecyclerView.setAdapter(new MessageGroupAdapter(messages, this));
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        messageRecyclerView.setHasFixedSize(true);
    }
    @Override
    public void onClick(MessageGroup messageGroup) {
        Intent intent = new Intent(getActivity(), MessageItemActivity.class);
        Gson gson = new Gson();
        intent.putExtra("messageGroup", gson.toJson(messageGroup));
        startActivity(intent);
    }
}