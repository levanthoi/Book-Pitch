package com.example.book_pitch.Models;

import java.util.List;

/**
 * @author Le Van Thoi
 * @serial title: Tên sân
 * @serial title: Tên sân
 *  */

public class Stadium {
    private String id;
    private String user_id;
    private String title;
    private String slug;
    private String address;
    private String about_us;
    private String opening_time;
    private String closing_time;
    private String average_rating;
    private List<String> images;
    private String phone;
//    private Location location;
    private int deleted;
    private String avatar;
    private int status;
//    private boolean created_at_human;
//    private int ball_available;
//    private int bibs_available;
//    private int shower_available;
//    private int washroom_available;
//    private int water_available;
//    private int wifi_available;
//    private boolean upfront_payment;
//    private boolean chat_with_customers;
//    private int ratedTimes;
//    private String discount;
//    private List<String> supported_payment_methods;
//    private String facebook_url;
//    private String instagram_url;
//    private String twitter_url;
//    private String whatsapp;
//    private String website;

    public Stadium() {
    }


    public Stadium(String id,String user_id, String title, String slug, String address, String about_us, String opening_time, String closing_time, String average_rating, List<String> images, String phone, int status, int deleted) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.slug = slug;
        this.address = address;
        this.about_us = about_us;
        this.opening_time = opening_time;
        this.closing_time = closing_time;
        this.average_rating = average_rating;
        this.images = images;
        this.phone = phone;
        this.deleted = deleted;
        this.status = status;
    }

    public Stadium(String id, String title, String average_rating, String phone, String address, String avatar) {
        this.id = id;
        this.title = title;
        this.average_rating = average_rating;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getAbout_us() {
        return about_us;
    }

    public void setAbout_us(String about_us) {
        this.about_us = about_us;
    }

    public String getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(String opening_time) {
        this.opening_time = opening_time;
    }

    public String getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(String average_rating) {
        this.average_rating = average_rating;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Stadium{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                // Các trường khác cần hiển thị...
                '}';
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
