package com.example.book_pitch.Activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Adapters.ChatMessageAdapter;
import com.example.book_pitch.Models.ChatMessage;
import com.example.book_pitch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatMessageAdapter adapter;
    private List<ChatMessage> messageList;
//    private DatabaseReference messagesRef;
    private EditText messageInput;
    private ImageButton sendButton;
    DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference().child("messages");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        recyclerView = findViewById(R.id.chat_recycler_view);
        messageInput = findViewById(R.id.chat_message_input);
        sendButton = findViewById(R.id.message_send_btn);

        messageList = new ArrayList<>();
        adapter = new ChatMessageAdapter(this, messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        messagesRef = FirebaseDatabase.getInstance().getReference().child("messages");



        // Đăng ký ValueEventListener để lắng nghe các thay đổi trong node "messages"
        messagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa danh sách tin nhắn hiện tại để cập nhật dữ liệu mới từ Firebase
                messageList.clear();
                // Duyệt qua tất cả các DataSnapshot con trong dataSnapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu của mỗi tin nhắn và thêm vào danh sách
                    ChatMessage message = snapshot.getValue(ChatMessage.class);
                    messageList.add(message);
                }
                // Thông báo cho adapter biết dữ liệu đã thay đổi
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase
                Toast.makeText(ChatActivity.this, "Failed to load messages: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




        sendButton.setOnClickListener(view -> {
            String messageContent = messageInput.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                sendMessage(messageContent);
                messageInput.setText(""); // Clear input after sending message
            } else {
                Toast.makeText(ChatActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage(String messageContent) {
        String messageId = messagesRef.push().getKey();
        if (messageId != null) {
            long timestamp = System.currentTimeMillis();
            ChatMessage message = new ChatMessage("nam","hello",timestamp);
            messagesRef.child(messageId).setValue(message);
        }
    }
}

