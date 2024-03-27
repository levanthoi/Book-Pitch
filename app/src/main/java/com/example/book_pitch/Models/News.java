package com.example.book_pitch.Models;

public class News {
    private String title;
    private String image;
    private String description;
    private String date;
    private int share;

    public News() {
    }

    public News(String title, String image, String description, String date) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.date = date;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }
}
