package com.example.book_pitch.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.book_pitch.Activities.MessageItemActivity;
import com.example.book_pitch.Adapters.MessageGroupAdapter;
import com.example.book_pitch.Models.ChatMessage;
import com.example.book_pitch.Models.MessageGroup;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.FirebaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment implements MessageGroupAdapter.MessageGroupAdapterOnClickHandler {
    private RecyclerView messageRecyclerView;
    private ArrayList<MessageGroup> messages;
    private MessageGroup messageGroup;
    private DatabaseReference messageGroupRef ;
    private MessageGroupAdapter messageGroupAdapter;


    public MessageFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);


        init(view);
        return view;
    }

    private void init(View view) {
        messageRecyclerView = view.findViewById(R.id.messageRecyclerView);
        messages = new ArrayList<>();
        messageGroupRef  = FirebaseDatabase.getInstance().getReference();
        // Thực hiện lắng nghe sự thay đổi của dữ liệu
        messageGroupRef .child("message_group").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageGroup message = snapshot.getValue(MessageGroup.class);
                    if (message.getMembers().contains(FirebaseUtil.currentUserId())) {
                        messages.add(message);
                    }
                    if (messageGroup != null) {
                        String lastMessage = getLastMessage(messageGroup.getId());
                        if (lastMessage != null) {
                            messageGroup.setLast_message(lastMessage);
                            // Cập nhật lại last_message trong cơ sở dữ liệu
                            messageGroupRef.child(messageGroup.getId()).child("last_message").setValue(lastMessage);
                        }
                        messages.add(message);
                    }

                }
                messageGroupAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load messages.", Toast.LENGTH_SHORT).show();
            }
        });
        messageGroupAdapter = new MessageGroupAdapter(messages, this);
        messageRecyclerView.setAdapter(messageGroupAdapter);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        messageRecyclerView.setHasFixedSize(true);


    }


    @Override
    public void onClick(MessageGroup message) {
        if (message != null) {
            Intent intent = new Intent(getActivity(), MessageItemActivity.class);
            Gson gson = new Gson();

            // Lấy danh sách các thành viên trong message
            List<String> members = message.getMembers();

            // Kiểm tra nếu có ít nhất một thành viên trong danh sách
            if (members != null && !members.isEmpty()) {
                // Duyệt qua từng thành viên trong danh sách
                for (String memberId : members) {
                    // Nếu memberId không phải là currentUserId
                    if (!memberId.equals(FirebaseUtil.currentUserId())) {
                        // Truy vấn Firestore Database để lấy thông tin của người nhận
                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                        DocumentReference userRef = firestore.collection("users").document(memberId);
                        userRef.get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null && document.exists()) {
                                    String toUserName = document.getString("displayName");
                                    message.setName(toUserName);
                                    intent.putExtra("messageGroup", gson.toJson(message));
                                    startActivity(intent);
                                } else {
                                    // Handle null document or document doesn't exist
                                }
                            } else {
                                // Handle task failure
                            }
                        });
                        // Sau khi tìm thấy memberId của người nhận, dừng vòng lặp
                        break;
                    }
                }
            } else {
                // Handle empty members list
            }
        } else {
            // Handle null message
        }
    }

    private String getLastMessage(String groupId) {
        DatabaseReference chatMessageRef = FirebaseDatabase.getInstance().getReference().child("chat_message");
        Query query = chatMessageRef.orderByChild("id_group").equalTo(groupId).limitToLast(1);
        final String[] lastMessage = {null};
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                    if (chatMessage != null) {
                        lastMessage[0] = chatMessage.getContent();
                        break;
                    }
                }
                // Sau khi lấy được tin nhắn mới nhất, cập nhật lại RecyclerView
                messageGroupAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
                Toast.makeText(getContext(), "Failed to get last message.", Toast.LENGTH_SHORT).show();
            }
        });
        return lastMessage[0];
    }




}
