package com.example.book_pitch.Models;


/**
 * ChatMessage
 * id - duy nhất
 * message: tin nhắn chat
 * id_group: id tham chiếu bên message_group
 * from_id: id_user của người gửi
 * to_id: id_của người nhận
 * \\\\\\\\\\\\\\\\\\\\
 * Check nếu auth_id == from_id || auth_id == to_id thì hiển thị bên phải
 * time: thời gian gửi tin nhắn: có thể lấy theo milis.
 * */

public class ChatMessage {

        private String sender;
        private String message;
        private long timestamp;
    public ChatMessage(){

    }
    public ChatMessage(String sender,String message,long timestamp) {
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }
    public String getSender() {
        return sender;
    }



    public String getMessage() {
        return message;
    }



    public long getTimestamp() {
        return timestamp;
    }

    public boolean isCurrentUser(String currentUserId) {
        return sender.equals(currentUserId);
    }

}

