package com.example.book_pitch.Models;


import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ Bill - Order
 * int      id              - id hóa đơn
 * String   user_id         - id user người đặt
 * Price    price           - chi tiết giá
 * String   pitch_id        - id sân bóng con
 * int      status          - Trạng thái (pending, confirm, completed, failed)
 * String   transactionToken- Mã token của giao dịch thanh toán (zptranstoken)
 * Date createdAt           - Thời gian tạo hóa đơn
 * Date updatedAt           - Thời gian cập nhật hóa đơn
 * int      deleted         - Xóa
 * */

public class Bill {
    private int id;
    private String user_id;
    private Price price;
    private String pitch_id;
    private int status;
    private String transactionToken;
    private int deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public Bill() {
    }

    public Bill(int id, String user_id, Price price, String pitch_id, int status, String transactionToken, int deleted, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user_id = user_id;
        this.price = price;
        this.pitch_id = pitch_id;
        this.status = status;
        this.transactionToken = transactionToken;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getPitch_id() {
        return pitch_id;
    }

    public void setPitch_id(String pitch_id) {
        this.pitch_id = pitch_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTransactionToken() {
        return transactionToken;
    }

    public void setTransactionToken(String transactionToken) {
        this.transactionToken = transactionToken;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
