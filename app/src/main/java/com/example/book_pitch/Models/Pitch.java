package com.example.book_pitch.Models;

import java.util.List;

public class Pitch {
    private int id;
    private String label;
    private int pitch_size;
    private int min_players;
    private int price;
    private int stadium_id;
    private int price_60;
    private int price_90;
    private int price_120;
    private boolean is_stackable;
    private boolean is_visible;
//    private List<Object> discounts;
//    private Upfront upfront;
    private List<Price> prices;

    public Pitch(int id, String label, int pitch_size) {
        this.id = id;
        this.label = label;
        this.pitch_size = pitch_size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getPitch_size() {
        return pitch_size;
    }

    public void setPitch_size(int pitch_size) {
        this.pitch_size = pitch_size;
    }

    public int getMin_players() {
        return min_players;
    }

    public void setMin_players(int min_players) {
        this.min_players = min_players;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStadium_id() {
        return stadium_id;
    }

    public void setStadium_id(int stadium_id) {
        this.stadium_id = stadium_id;
    }

    public int getPrice_60() {
        return price_60;
    }

    public void setPrice_60(int price_60) {
        this.price_60 = price_60;
    }

    public int getPrice_90() {
        return price_90;
    }

    public void setPrice_90(int price_90) {
        this.price_90 = price_90;
    }

    public int getPrice_120() {
        return price_120;
    }

    public void setPrice_120(int price_120) {
        this.price_120 = price_120;
    }

    public boolean isIs_stackable() {
        return is_stackable;
    }

    public void setIs_stackable(boolean is_stackable) {
        this.is_stackable = is_stackable;
    }

    public boolean isIs_visible() {
        return is_visible;
    }

    public void setIs_visible(boolean is_visible) {
        this.is_visible = is_visible;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }
}
