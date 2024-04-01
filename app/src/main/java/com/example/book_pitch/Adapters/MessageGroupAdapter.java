package com.example.book_pitch.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Models.MessageGroup;
import com.example.book_pitch.R;

import java.util.List;

public class MessageGroupAdapter extends RecyclerView.Adapter<MessageGroupAdapter.MyViewHolder> {
    private final List<MessageGroup> messagesLists;
    private final MessageGroupAdapterOnClickHandler clickHandler;
    public interface MessageGroupAdapterOnClickHandler{
        void onClick(MessageGroup messages);
    }
    public MessageGroupAdapter(List<MessageGroup> messagesLists, MessageGroupAdapterOnClickHandler clickHandler) {
        this.messagesLists = messagesLists;
        this.clickHandler = clickHandler;
    }
    @NonNull
    @Override
    public MessageGroupAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_messages,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull MessageGroupAdapter.MyViewHolder holder, int position) {
        holder.name.setText(messagesLists.get(position).getName());
        holder.lastMessage.setText(messagesLists.get(position).getLastMessages());
        holder.unseenMessages.setText(String.valueOf(messagesLists.get(position).getUnseenMessages()));



    }
    @Override
    public int getItemCount() {
        return messagesLists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView profilePic;
        private TextView name;
        private TextView lastMessage;
        private TextView unseenMessages;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePic);
            name = itemView.findViewById(R.id.name);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            unseenMessages = itemView.findViewById(R.id.unseenMessages);
        }


    }
}
