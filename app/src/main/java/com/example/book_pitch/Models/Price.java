package com.example.book_pitch.Models;

public class Price {
    private String id;
    private String pitch_id;
    private String from_date;
    private String to_date;
    private int duration;
    private String from_time;
    private String to_time;
    private String price;

    public Price() {
    }

    public Price(String id, String pitch_id, String from_date, String to_date, int duration, String from_time, String to_time, String price) {
        this.id = id;
        this.pitch_id = pitch_id;
        this.from_date = from_date;
        this.to_date = to_date;
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

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
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
