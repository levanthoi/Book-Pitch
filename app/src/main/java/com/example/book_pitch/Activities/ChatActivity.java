package com.example.book_pitch.Activities;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.book_pitch.Models.User;
import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private EditText messageInput;
    private ImageButton sendButton;

    private User currentUser;
    DatabaseReference messageGroupRef;
    private ValueEventListener messageListener;
    private String timestamp;
    private String currentUserId;
//    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);


        // Khởi tạo messageGroupRef
        Bundle args = getIntent().getExtras();
        if (args != null) {
            String groupId = args.getString("groupId");
            messageGroupRef = FirebaseDatabase.getInstance().getReference().child("chat_message").child(groupId);
        }

        recyclerView = findViewById(R.id.chat_recycler_view);
        messageInput = findViewById(R.id.chat_message_input);
        sendButton = findViewById(R.id.message_send_btn);

        messageList = new ArrayList<>();
        currentUserId = getCurrentUserId();
        adapter = new ChatMessageAdapter(this, messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        attachDatabaseListener();
        loadCurrentUser();

        sendButton.setOnClickListener(view -> {
            String messageContent = messageInput.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                sendMessage(messageContent);
                messageInput.setText(""); // Clear input after sending message
            }
        });
    }

    private String getCurrentUserId() {
        Intent intent = getIntent();
        if (intent != null) {
            String userIdFromIntent = intent.getStringExtra("userId");
            if (userIdFromIntent != null && !userIdFromIntent.isEmpty()) {
                return userIdFromIntent;
            }
        }

        // Nếu không lấy được từ Intent, thử lấy từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("YourSharedPreferencesName", Context.MODE_PRIVATE);
        String userIdFromSharedPreferences = sharedPreferences.getString("userId", null);
        if (userIdFromSharedPreferences != null && !userIdFromSharedPreferences.isEmpty()) {
            return userIdFromSharedPreferences;
        }

        // Nếu không lấy được từ cả Intent và SharedPreferences, trả về giá trị mặc định (ví dụ: null)
        return null;
    }

    private void attachDatabaseListener() {
        if (messageGroupRef != null) {
            messageListener = messageGroupRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ChatMessage message = snapshot.getValue(ChatMessage.class);
                        if (message != null) {
                            messageList.add(message);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    // Scroll to the bottom of the RecyclerView to show the latest message
                    recyclerView.smoothScrollToPosition(messageList.size() - 1);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ChatActivity.this, "Failed to load messages: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void sendMessage(String messageContent) {
        String messageId = messageGroupRef.push().getKey();
        if (messageId != null && currentUser != null && messageGroupRef != null) {
            long timestamp = System.currentTimeMillis();
            Bundle args = getIntent().getExtras();
            if (args != null) {
                String groupId = args.getString("groupId");
                ChatMessage message = new ChatMessage(messageId, groupId, currentUser.getId(), "", messageContent, String.valueOf(timestamp));
                messageGroupRef.child(messageId).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Firebase", "Message sent successfully.");
                            // Sau khi gửi tin nhắn thành công, cập nhật RecyclerView
                            messageList.add(message);
                            adapter.notifyDataSetChanged();
                            // Scroll đến vị trí cuối cùng để hiển thị tin nhắn mới
                            recyclerView.scrollToPosition(messageList.size() - 1);
                        } else {
                            Log.e("Firebase", "Failed to send message: " + task.getException());
                            Toast.makeText(ChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Log.e("ChatFragment", "Bundle args is null");
            }
        }
    }
    private void loadCurrentUser() {
        String userId = getCurrentUserId();
        if (userId != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        currentUser = dataSnapshot.getValue(User.class);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ChatActivity.this, "Failed to load user data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (messageGroupRef != null && messageListener != null) {
            messageGroupRef.removeEventListener(messageListener);
        }
    }
}



