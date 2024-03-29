package com.example.book_pitch.Models;

public class Price {
    private String id;
    private String pitch_id;
    private String date;
    private int duration;
    private String from_time;
    private String to_time;
    private String price;

    public Price() {
    }

    public Price(String id, String pitch_id, String date, int duration, String from_time, String to_time, String price) {
        this.id = id;
        this.pitch_id = pitch_id;
        this.date = date;
        this.duration = duration;
        this.from_time = from_time;
        this.to_time = to_time;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPitch_id() {
        return pitch_id;
    }

    public void setPitch_id(String pitch_id) {
        this.pitch_id = pitch_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
