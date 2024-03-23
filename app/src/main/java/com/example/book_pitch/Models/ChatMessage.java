package com.example.book_pitch.Models;

public class ChatMessage {

        private String sender;
        private String message;
        private String timestamp;
    public ChatMessage(){

    }
    public ChatMessage(String sender,String message,String timestamp) {
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



    public String getTimestamp() {
        return timestamp;
    }


}

