package com.example.book_pitch.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatMessageAdapter adapter;
    private List<ChatMessage> messageList;
    private EditText messageInput;
    private ImageButton sendButton;

    DatabaseReference conversationsRef = FirebaseDatabase.getInstance().getReference().child("messages");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);
        connectToFirebase();
        recyclerView = findViewById(R.id.chat_recycler_view);
        messageInput = findViewById(R.id.chat_message_input);
        sendButton = findViewById(R.id.message_send_btn);

        messageList = new ArrayList<>();

        // Log để kiểm tra xem messageList có dữ liệu hay không
        Log.d("MessageListSize", "Size: " + messageList.size());

        adapter = new ChatMessageAdapter(this, messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        String userId = "user1"; // Replace with the current user's ID
        String recipientId = "user2"; // Replace with the recipient's ID
        // Construct the path for messages between the two users
        String chatPath = "messages/" + userId + "_" + recipientId;
        conversationsRef = FirebaseDatabase.getInstance().getReference().child(chatPath);
        // Đăng ký ValueEventListener để lắng nghe các thay đổi trong node "messages"
        conversationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa danh sách tin nhắn hiện tại để cập nhật dữ liệu mới từ Firebase
                messageList.clear();
                // Duyệt qua tất cả các DataSnapshot con trong dataSnapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu của mỗi tin nhắn và thêm vào danh sách
                    ChatMessage message = snapshot.getValue(ChatMessage.class);
                    if (message != null) {
                        messageList.add(message);
                    }
                }
                // Thông báo cho adapter biết dữ liệu đã thay đổi
                adapter.notifyDataSetChanged();

                // Kiểm tra nếu danh sách tin nhắn không rỗng
                if (!messageList.isEmpty()) {
                    // Log danh sách tin nhắn vào Logcat để kiểm tra
                    for (ChatMessage message : messageList) {
                        Log.d("Message", "Sender: " + message.getSender() + ", Message: " + message.getMessage());
                    }
                } else {
                    // Nếu danh sách tin nhắn rỗng, hiển thị thông báo
                    Toast.makeText(ChatActivity.this, "No messages available", Toast.LENGTH_SHORT).show();
                }
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

    private void connectToFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }

    private void sendMessage(String messageContent) {
        String messageId = conversationsRef.push().getKey();
        if (messageId != null) {
            long timestamp = System.currentTimeMillis();
            ChatMessage message = new ChatMessage("nam", "thời",messageContent, timestamp);
            conversationsRef.child(messageId).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("Firebase", "Message sent successfully.");
                    } else {
                        Log.e("Firebase", "Failed to send message: " + task.getException());
                        Toast.makeText(ChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



}

