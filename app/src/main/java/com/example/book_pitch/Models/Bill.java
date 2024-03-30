package com.example.book_pitch.Models;

public class Bill {
    private Price price;
    private String title;
    private int pitch_size;
    private String label;
    private String address;
    private String phone;

    public Bill() {
    }

    public Bill(Price price, String title, int pitch_size, String label, String address, String phone) {
        this.price = price;
        this.title = title;
        this.pitch_size = pitch_size;
        this.label = label;
        this.address = address;
        this.phone = phone;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPitch_size() {
        return pitch_size;
    }

    public void setPitch_size(int pitch_size) {
        this.pitch_size = pitch_size;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
