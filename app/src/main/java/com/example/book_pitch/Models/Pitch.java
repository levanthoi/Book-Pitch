package com.example.book_pitch.Models;

import java.util.List;

/**
 *  Pitch
 *  ==========
 * int      id              - ID của sân bóng
 * String   label           - Nhãn của sân bóng
 * int      pitch_size      - Kích thước của sân bóng
 * List<Integer> durations  - Danh sách các khoảng thời gian có sẵn cho đặt sân
 * String   stadium_id      - ID của sân vận động chứa sân bóng
 * int      status          - Trạng thái của sân bóng (pending, confirmed, etc.)
 * List<Price> prices       - Danh sách giá cho các khoảng thời gian đặt sân
 *
 * */


public class Pitch {
    private String id;
    private String label;
    private int pitch_size;
    private List<Integer> durations;
    private String stadium_id;
    private int status;
    private List<Price> prices;

    public Pitch() {
    }

    public Pitch(String id, String label, int pitch_size,List<Integer> durations, String stadium_id, int status, List<Price> prices) {
        this.id = id;
        this.label = label;
        this.pitch_size = pitch_size;
        this.durations = durations;
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

    public List<Integer> getDurations() {
        return durations;
    }

    public void setDurations(List<Integer> durations) {
        this.durations = durations;
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
