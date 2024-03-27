package com.example.book_pitch.Models;

public class Bill {
    private int id;
    private int pitchId;
    private String name;
    private Float price;
    private String beginTime;
    private String address;
    private String endTime;
    private String phone;
    private String status;
    public Bill() {

    }
    public Bill(int id, int pitchId, String name, Float price, String beginTime, String address, String endTime, String phone, String status) {
        this.id = id;
        this.pitchId = pitchId;
        this.name = name;
        this.price = price;
        this.beginTime = beginTime;
        this.address = address;
        this.endTime = endTime;
        this.phone = phone;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPitchId() {
        return pitchId;
    }

    public void setPitchId(int pitchId) {
        this.pitchId = pitchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
