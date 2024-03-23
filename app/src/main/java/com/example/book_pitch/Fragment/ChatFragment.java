package com.example.book_pitch.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Adapters.ChatMessageAdapter;
import com.example.book_pitch.Models.ChatMessage;
import com.example.book_pitch.R;

import java.util.ArrayList;
import java.util.List;

    public class ChatFragment extends Fragment {
    private List<ChatMessage> messagesList;
    private ChatMessageAdapter adapter;

    public ChatFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        EditText messageEditTxt = view.findViewById(R.id.messageEditTxt);
        ImageView sendBtn = view.findViewById(R.id.sendBtn);
        RecyclerView recyclerView = view.findViewById(R.id.rcvmessages);
        messagesList = new ArrayList<>();
        messagesList.add(new ChatMessage("User 1", "Hello!", "09:00 AM"));
        messagesList.add(new ChatMessage("User 2", "Hi there!", "09:05 AM"));
        messagesList.add(new ChatMessage("User 1", "How are you?", "09:10 AM"));
        adapter = new ChatMessageAdapter(messagesList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditTxt.getText().toString().trim();
                if (!message.isEmpty()) {
                    ChatMessage newMessage = new ChatMessage("nam", message, getCurrentTimestamp());
                    messagesList.add(newMessage);
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(messagesList.size() - 1);
                    messageEditTxt.setText("");
                }
            }
        });

        return view;
    }
    private String getCurrentTimestamp() {
        return "";
    }
}
