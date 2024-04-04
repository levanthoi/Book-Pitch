package com.example.book_pitch.Models;

import com.google.firebase.database.DataSnapshot;

public class ChatMessage {

        private String sender;
        private String content;

        private String receiver;
        private long timestamp;
    public ChatMessage(){

    }
    public ChatMessage(String sender,String receiver,String content,long timestamp) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.receiver = receiver;
    }
    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return content;
    }



    public long getTimestamp() {
        return timestamp;
    }

    public static ChatMessage fromSnapshot(DataSnapshot dataSnapshot) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.sender = dataSnapshot.child("sender").getValue(String.class);
        chatMessage.content = dataSnapshot.child("content").getValue(String.class);
        chatMessage.receiver = dataSnapshot.child("receiver").getValue(String.class);
        chatMessage.timestamp = dataSnapshot.child("timestamp").getValue(Long.class);
        return chatMessage;
    }

    public boolean isCurrentUser(String currentUserId) {
        return sender.equals(currentUserId);
    }

}

