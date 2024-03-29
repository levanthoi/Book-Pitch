package com.example.book_pitch.Models;

public class MessageGroup {
    private String name,mobile, lastMessages;
    private int unseenMessages;

    public MessageGroup() {
    }

    public MessageGroup(String name, String mobile, String lastMessages, int unseenMessages) {
        this.name = name;
        this.mobile = mobile;
        this.lastMessages = lastMessages;
        this.unseenMessages = unseenMessages;
    }


    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLastMessages() {
        return lastMessages;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }
}
