package com.example.book_pitch.Models;

import java.util.List;

public class Pitch {
    private String id;
    private String label;
    private int pitch_size;
    private String stadium_id;
    private int status;
    private List<Price> prices;

    public Pitch() {
    }

    public Pitch(String id, String label, int pitch_size, String stadium_id, int status, List<Price> prices) {
        this.id = id;
        this.label = label;
        this.pitch_size = pitch_size;
        this.stadium_id = stadium_id;
        this.status = status;
        this.prices = prices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getStadium_id() {
        return stadium_id;
    }

    public void setStadium_id(String stadium_id) {
        this.stadium_id = stadium_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }
}
