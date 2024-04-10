package com.example.book_pitch.Models;

public class Review {
    private String id;
    private String user_id;
    private String stadium_id;
    private String pitch_id;
    private String comment;
    private String rating;
    private String status;
    private int deleted;
    private String created_at;
    private String updated_at;

    public Review() {
    }

    public Review(String id, String user_id, String stadium_id, String pitch_id, String comment, String rating, String status, int deleted, String created_at, String updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.stadium_id = stadium_id;
        this.pitch_id = pitch_id;
        this.comment = comment;
        this.rating = rating;
        this.status = status;
        this.deleted = deleted;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStadium_id() {
        return stadium_id;
    }

    public void setStadium_id(String stadium_id) {
        this.stadium_id = stadium_id;
    }

    public String getPitch_id() {
        return pitch_id;
    }

    public void setPitch_id(String pitch_id) {
        this.pitch_id = pitch_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
