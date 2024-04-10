package com.example.book_pitch.Activities;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_pitch.Adapters.ChatMessageAdapter;
import com.example.book_pitch.Adapters.MessageGroupAdapter;
import com.example.book_pitch.Models.ChatMessage;
import com.example.book_pitch.Models.MessageGroup;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageItemActivity extends AppCompatActivity {
    private MessageGroup messageGroup;
    private DatabaseReference chatMessageRef ;
    private ChatMessageAdapter chatMessageAdapter;


    private ChatMessage chatMessage;
    private List<ChatMessage> chat_messages;
    private ImageButton btnSearch;
    private EditText messageInput;
    private RecyclerView chat_recycler_view;
    private ImageButton back_btn;
    private ImageButton sendButton;
    private TextView other_username;
    DatabaseReference messageGroupRef;
    String chatMessageJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);
        handleIntent(getIntent());
        handleClick();
        chat_messages = new ArrayList<>();
        chatMessageRef  = FirebaseDatabase.getInstance().getReference().child("chat_message");
        chat_recycler_view = findViewById(R.id.chat_recycler_view);
        sendButton = findViewById(R.id.message_send_btn);

        getKeyUnderMessageGroup();

        messageInput = findViewById(R.id.chat_message_input);

        handleHeader();
        Data();
        getToId(messageGroup,FirebaseUtil.currentUserId());

        sendButton.setOnClickListener(view -> {
            String messageContent = messageInput.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                sendMessage(messageContent);
                messageInput.setText(""); // Clear input after sending message
            }
        });

    }
    private void handleIntent(Intent intent) {
        if(intent != null && intent.hasExtra("messageGroup")){
            String messageGroupJson = intent.getStringExtra("messageGroup");
            Gson gson = new Gson();
            messageGroup = gson.fromJson(messageGroupJson, MessageGroup.class);

        }
    }






    private void handleClick(){
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void handleHeader(){
        other_username = findViewById(R.id.other_username);
        if (messageGroup != null) {
            other_username.setText(messageGroup.getName());
        }
    }
    public String getToId(MessageGroup messageGroup, String currentUserId) {
        List<String> members = messageGroup.getMembers();
        for (String memberId : members) {
            if (!memberId.equals(currentUserId)) {
                return memberId;
            }
        }
        return null;
    }

    private String getKeyUnderMessageGroup() {
        DatabaseReference messageGroupRef = FirebaseDatabase.getInstance().getReference().child("message_group");
        messageGroupRef.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();
//                        sendMessage(key);
//                        return;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return null;
    }


    private void Data(){
        if(messageGroup!=null){
            chatMessageRef .orderByChild("id_group").equalTo(messageGroup.getId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    chat_messages.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ChatMessage chat_message = snapshot.getValue(ChatMessage.class);
                            chat_messages.add(chat_message);
                    }
                    chatMessageAdapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MessageItemActivity.this, "Failed to load chat_message.", Toast.LENGTH_SHORT).show();
                }
            });
            chatMessageAdapter = new ChatMessageAdapter(MessageItemActivity.this,chat_messages);
            chat_recycler_view.setAdapter(chatMessageAdapter);
            chat_recycler_view.setLayoutManager(new LinearLayoutManager(MessageItemActivity.this, LinearLayoutManager.VERTICAL, false));
            chat_recycler_view.setHasFixedSize(true);
        }
    }

    private void sendMessage(String messageContent) {
            long timestamp = System.currentTimeMillis();
            Bundle args = getIntent().getExtras();
            if (args != null) {
                String groupId = messageGroup.getId();
                String id = String.valueOf(timestamp);
                String toId = getToId(messageGroup, FirebaseUtil.currentUserId());
                ChatMessage message = new ChatMessage(id, groupId, FirebaseUtil.currentUserId(), toId, messageContent, String.valueOf(timestamp));
                chatMessageRef.child(id).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Firebase", "Message sent successfully.");
                            Data();
                            // Scroll đến vị trí cuối cùng để hiển thị tin nhắn mới
                            chat_recycler_view.scrollToPosition(chat_messages.size() - 1);
                        } else {
                            Log.e("Firebase", "Failed to send message: " + task.getException());
                            Toast.makeText(MessageItemActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Log.e("ChatFragment", "Bundle args is null");
            }

    }
}