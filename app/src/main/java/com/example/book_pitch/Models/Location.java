package com.example.book_pitch.Models;

public class Location {
    private int id;
    private String area;
    private String city;

    public Location(int id, String area) {
        this.id = id;
        this.area = area;
    }

    public Location(int id, String area, String city) {
        this.id = id;
        this.area = area;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
