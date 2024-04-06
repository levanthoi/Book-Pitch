package com.example.book_pitch.Fragment;



import android.content.Intent;
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

public class ChatFragment extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatMessageAdapter adapter;
    private List<ChatMessage> messageList;
    private EditText messageInput;
    private ImageButton sendButton;

    private User currentUser;
    DatabaseReference messageGroupRef;

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

        Bundle args = getIntent().getExtras();
        if (args != null) {
            String groupId = args.getString("groupId");
            String groupName = args.getString("groupName");
            messageGroupRef = FirebaseDatabase.getInstance().getReference().child("messages").child(groupId);
        }

        sendButton.setOnClickListener(view -> {
            String messageContent = messageInput.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                sendMessage(messageContent);
                messageInput.setText(""); // Clear input after sending message
            } else {
                Toast.makeText(ChatFragment.this, "Please enter a message", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage(String messageContent) {
        String messageId = messageGroupRef.push().getKey();
        if (messageId != null && currentUser != null && messageGroupRef != null) {
            long timestamp = System.currentTimeMillis();
            Bundle args = getIntent().getExtras();
            if (args != null) {
                String groupId = args.getString("groupId");
                ChatMessage message = new ChatMessage(messageId, groupId, currentUser.getId(), "", messageContent, timestamp);
                messageGroupRef.child(messageId).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Firebase", "Message sent successfully.");
                        } else {
                            Log.e("Firebase", "Failed to send message: " + task.getException());
                            Toast.makeText(ChatFragment.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Log.e("ChatFragment", "Bundle args is null");
            }
        }
    }

    private void getUserDataFromFirebase(String userId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentUser = dataSnapshot.getValue(User.class);
                    if (currentUser != null) {
                        Log.d("UserData", "Display Name: " + currentUser.getDisplayName());
                    }
                } else {
                    Log.d("UserData", "User data does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatFragment.this, "Failed to load user data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}





