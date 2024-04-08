package com.example.book_pitch.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.R;

public class ChatFragment extends Fragment {
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.chat_recycler_view);
//        setupRecyclerView();
        return view;
    }
//    void setupRecyclerView(){
//        Query query = FirebaseUtil.allChatroomCollectionReference()
//                .whereArrayContains("userIds",FirebaseUtil.currentUserId())
//                .orderBy("lastMessageTimestamp",Query.Direction.DESCENDING);
//
//        FirestoreRecyclerOptions<ChatroomModel> options = new FirestoreRecyclerOptions.Builder<ChatroomModel>()
//                .setQuery(query,ChatroomModel.class).build();
//
//        adapter = new RecentChatRecyclerAdapter(options,getContext());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if(adapter!=null)
//            adapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if(adapter!=null)
//            adapter.stopListening();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if(adapter!=null)
//            adapter.notifyDataSetChanged();
//    }


}
