package com.example.book_pitch.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.book_pitch.Activities.MessageItemActivity;
import com.example.book_pitch.Adapters.MessageGroupAdapter;
import com.example.book_pitch.Models.MessageGroup;
import com.example.book_pitch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MessageFragment extends Fragment implements MessageGroupAdapter.MessageGroupAdapterOnClickHandler {
    private RecyclerView messageRecyclerView;
    private ArrayList<MessageGroup> messages;
    private DatabaseReference mDatabase;
    private MessageGroupAdapter messageGroupAdapter;

    public MessageFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        messageRecyclerView = view.findViewById(R.id.messageRecyclerView);

        messages = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Thực hiện lắng nghe sự thay đổi của dữ liệu
        mDatabase.child("message_group").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageGroup message = snapshot.getValue(MessageGroup.class);
                    messages.add(message);
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
        Intent intent = new Intent(getActivity(), MessageItemActivity.class);
        Gson gson = new Gson();
        intent.putExtra("messageGroup", gson.toJson(message));
        startActivity(intent);
    }

    @Override
    public void onClick(String groupId, String groupName) {
        // Chuyển sang fragment_chat và chuyển dữ liệu cần thiết
        Fragment fragment = new Fragment();
        Bundle args = new Bundle();
        args.putString("groupId", groupId);
        args.putString("groupName", groupName);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.toolbar, fragment).commit();
    }
}
