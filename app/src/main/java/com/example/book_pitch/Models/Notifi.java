package com.example.book_pitch.Models;

public class Notifi {
    private String title;

    private String image;

    private String time;

    private String content;

    public Notifi() {
    }

    public Notifi(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Notifi(String title, String image, String time, String content) {
        this.title = title;
        this.image = image;
        this.time = time;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
