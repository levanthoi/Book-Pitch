package com.example.book_pitch.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView; // Add this import statement

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Models.ChatMessage;
import com.example.book_pitch.R;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MyViewHolder> {
    private List<ChatMessage> messagesList;

    public ChatMessageAdapter(List<ChatMessage> messagesList) {
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatMessage message = messagesList.get(position);
        holder.senderTextView.setText(message.getSender());
        holder.messageTextView.setText(message.getMessage());
        holder.timestampTextView.setText(message.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView senderTextView;
        public TextView messageTextView;
        public TextView timestampTextView;

        public MyViewHolder(View view) {
            super(view);
            senderTextView = view.findViewById(R.id.name);
            messageTextView = view.findViewById(R.id.messageEditTxt);
            timestampTextView = view.findViewById(R.id.time);
        }
    }
}
