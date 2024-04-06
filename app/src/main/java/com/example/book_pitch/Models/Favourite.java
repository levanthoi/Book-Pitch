package com.example.book_pitch.Models;

public class Favourite {
    private String id;
    private String title;
    private String address;
    private String phone;
    private String rating;
    private String avatar;

    public Favourite(String key) {

    }
    public Favourite(String id, String title, String address, String phone, String rating, String avatar) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.phone = phone;
        this.rating = rating;
        this.avatar = avatar;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
