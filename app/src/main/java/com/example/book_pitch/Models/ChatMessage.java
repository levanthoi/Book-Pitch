package com.example.book_pitch.Models;

import com.example.book_pitch.Utils.FirebaseUtil;
import com.google.firebase.database.DataSnapshot;

/**
 * ChatMessage
 * id - duy nhất
 * content: tin nhắn chat
 * id_group: id tham chiếu bên message_group
 * from_id: id_user của người gửi
 * to_id: id_của người nhận
 * \\\\\\\\\\\\\\\\\\\\
 * Check nếu auth_id == from_id || auth_id == to_id thì hiển thị bên phải
 * time: thời gian gửi tin nhắn: có thể lấy theo milis.
 * */



public class ChatMessage {
        private String id;
        private String id_group;

        private String from_id;
        private String content;
        private String to_id;
        private String timestamp;


    public ChatMessage(){

    }
    public ChatMessage(String id,String id_group,String from_id,String to_id,String content,String timestamp) {
        this.id = id;
        this.id_group = id_group;
        this.from_id = from_id;
        this.content = content;
        this.timestamp = timestamp;
        this.to_id = to_id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_group() {
        return id_group;
    }

    public void setId_group(String id_group) {
        this.id_group = id_group;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static ChatMessage fromSnapshot(DataSnapshot dataSnapshot) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.from_id = dataSnapshot.child("from_id").getValue(String.class);
        chatMessage.content = dataSnapshot.child("content").getValue(String.class);
        chatMessage.to_id = dataSnapshot.child("to_id").getValue(String.class);
        chatMessage.timestamp = dataSnapshot.child("timestamp").getValue(String.class);
        return chatMessage;
    }

    public boolean isCurrentUser() {
        return from_id.equals(FirebaseUtil.currentUserId());
    }

}

