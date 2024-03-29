package com.example.book_pitch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Models.ChatMessage;
import com.example.book_pitch.R;

import java.util.List;


public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MessageViewHolder> {

    private List<ChatMessage> messageList;
    private Context context;

    public ChatMessageAdapter(Context context, List<ChatMessage> messageList) {
        this.context = context;
        this.messageList = messageList;
    }



    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_messages, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);
        if (message.isCurrentUser("1")) {
            holder.leftChatTextView.setVisibility(View.GONE);
            holder.rightChatTextView.setVisibility(View.VISIBLE);
            holder.rightChatTextView.setText(message.getMessage());
        } else {
            holder.rightChatTextView.setVisibility(View.GONE);
            holder.leftChatTextView.setVisibility(View.VISIBLE);
            holder.leftChatTextView.setText(message.getMessage());
        }
        long currentTimeMillis = System.currentTimeMillis();
        long timeInterval = 10 * 60 * 1000; // 10 phút
        long fakeTimestamp = currentTimeMillis - timeInterval;

        // Gán giá trị fake cho TextView hiển thị timestamp
        holder.leftChatTextView.setText(String.valueOf(fakeTimestamp));
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView leftChatTextView;
        TextView rightChatTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatTextView = itemView.findViewById(R.id.left_chat_textview);
            rightChatTextView = itemView.findViewById(R.id.right_chat_textview);
        }
    }
}
