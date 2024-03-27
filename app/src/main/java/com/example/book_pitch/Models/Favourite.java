package com.example.book_pitch.Models;

public class Favourite {
    private int id;
    private String title;
    private String address;
    private String phone;

    public Favourite() {

    }
    public Favourite(int id, String title, String address, String phone) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
