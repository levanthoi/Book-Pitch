package com.example.book_pitch.Models;

public class New {
    private String title;
    private String link;
    private String description;
    private String image;
    private String pubDate;

    public New() {
    }


    public New(String title, String link, String description, String image, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.image = image;
        this.pubDate = pubDate;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getpubDate() {
        return pubDate;
    }

    public void setpubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
