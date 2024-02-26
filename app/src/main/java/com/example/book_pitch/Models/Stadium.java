package com.example.book_pitch.Models;

import java.util.List;

public class Stadium {
    private int id;
    private String title;
    private String slug;
    private String about_us;
    private String notes;
    private String currency;
    private boolean is_offline;
    private String opening_time;
    private String closing_time;
    private boolean created_at_human;
    private int ball_available;
    private int bibs_available;
    private int shower_available;
    private int washroom_available;
    private int water_available;
    private int wifi_available;
    private boolean upfront_payment;
    private boolean chat_with_customers;
    private int ratedTimes;
    private String average_rating;
    private String discount;
    private List<String> images;
    private List<String> supported_payment_methods;
    private String facebook_url;
    private String instagram_url;
    private String twitter_url;
    private String whatsapp;
    private String website;
    private String phone;
    private int visible_on_web;
    private Location location;
    private List<Pitch> pitchs;

    public Stadium(int id, String title, String opening_time, String closing_time, String average_rating, String phone, Location location) {
        this.id = id;
        this.title = title;
        this.opening_time = opening_time;
        this.closing_time = closing_time;
        this.average_rating = average_rating;
        this.phone = phone;
        this.location = location;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isIs_offline() {
        return is_offline;
    }

    public void setIs_offline(boolean is_offline) {
        this.is_offline = is_offline;
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

    public boolean isCreated_at_human() {
        return created_at_human;
    }

    public void setCreated_at_human(boolean created_at_human) {
        this.created_at_human = created_at_human;
    }

    public int getBall_available() {
        return ball_available;
    }

    public void setBall_available(int ball_available) {
        this.ball_available = ball_available;
    }

    public int getBibs_available() {
        return bibs_available;
    }

    public void setBibs_available(int bibs_available) {
        this.bibs_available = bibs_available;
    }

    public int getShower_available() {
        return shower_available;
    }

    public void setShower_available(int shower_available) {
        this.shower_available = shower_available;
    }

    public int getWashroom_available() {
        return washroom_available;
    }

    public void setWashroom_available(int washroom_available) {
        this.washroom_available = washroom_available;
    }

    public int getWater_available() {
        return water_available;
    }

    public void setWater_available(int water_available) {
        this.water_available = water_available;
    }

    public int getWifi_available() {
        return wifi_available;
    }

    public void setWifi_available(int wifi_available) {
        this.wifi_available = wifi_available;
    }

    public boolean isUpfront_payment() {
        return upfront_payment;
    }

    public void setUpfront_payment(boolean upfront_payment) {
        this.upfront_payment = upfront_payment;
    }

    public boolean isChat_with_customers() {
        return chat_with_customers;
    }

    public void setChat_with_customers(boolean chat_with_customers) {
        this.chat_with_customers = chat_with_customers;
    }

    public int getRatedTimes() {
        return ratedTimes;
    }

    public void setRatedTimes(int ratedTimes) {
        this.ratedTimes = ratedTimes;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(String average_rating) {
        this.average_rating = average_rating;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getSupported_payment_methods() {
        return supported_payment_methods;
    }

    public void setSupported_payment_methods(List<String> supported_payment_methods) {
        this.supported_payment_methods = supported_payment_methods;
    }

    public String getFacebook_url() {
        return facebook_url;
    }

    public void setFacebook_url(String facebook_url) {
        this.facebook_url = facebook_url;
    }

    public String getInstagram_url() {
        return instagram_url;
    }

    public void setInstagram_url(String instagram_url) {
        this.instagram_url = instagram_url;
    }

    public String getTwitter_url() {
        return twitter_url;
    }

    public void setTwitter_url(String twitter_url) {
        this.twitter_url = twitter_url;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getVisible_on_web() {
        return visible_on_web;
    }

    public void setVisible_on_web(int visible_on_web) {
        this.visible_on_web = visible_on_web;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Pitch> getPitchs() {
        return pitchs;
    }

    public void setPitchs(List<Pitch> pitchs) {
        this.pitchs = pitchs;
    }
}
