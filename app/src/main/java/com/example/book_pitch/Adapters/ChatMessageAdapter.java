package com.example.book_pitch.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Models.ChatMessage;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.FirebaseUtil;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MessageViewHolder> {

    private List<ChatMessage> messageList;
    private Context mContext;


    public ChatMessageAdapter(Context context, List<ChatMessage> messageList) {
        this.mContext = context;
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
        holder.bind(message, FirebaseUtil.currentUserId());

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView leftChatTextView;
        LinearLayout rightChatLayout;
        LinearLayout leftChatLayout;
        TextView rightChatTextView;
        TextView timestampTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatTextView = itemView.findViewById(R.id.left_chat_textview);
            rightChatTextView = itemView.findViewById(R.id.right_chat_textview);
            leftChatLayout = itemView.findViewById(R.id.left_chat_layout);
            rightChatLayout = itemView.findViewById(R.id.right_chat_layout);
            timestampTextView = itemView.findViewById(R.id.timestamp_textview);
        }

        public void bind(ChatMessage message, String currentUserId) {
            if (message != null && currentUserId != null) {
                if (message.isCurrentUser()) {
                    rightChatTextView.setText(message.getContent());
                    rightChatLayout.setVisibility(View.VISIBLE);
                    leftChatLayout.setVisibility(View.GONE);
                } else {
                    leftChatTextView.setText(message.getContent());
                    leftChatLayout.setVisibility(View.VISIBLE);
                    rightChatLayout.setVisibility(View.GONE);
                }
//                // Hiển thị thời gian dưới dạng dd/MM/yyyy HH:mm:ss
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                String formattedDate = sdf.format(message.getTimestamp());
//                timestampTextView.setText(formattedDate);
            }
        }
    }
}


