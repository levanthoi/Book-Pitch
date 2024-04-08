package com.example.book_pitch.Models;

import androidx.annotation.NonNull;

import com.example.book_pitch.Activities.PaymentSuccessActivity;
import com.example.book_pitch.Utils.AndroidUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.Date;

/**
 * @ Bill - Order
 * int      id              - id hóa đơn
 * String   user_id         - id user người đặt
 * Price    price           - chi tiết giá
 * String   pitch_id        - id sân bóng con
 * String   stadium_id      - id sân bóng cha
 * int      status          - Trạng thái (pending: 0, confirm: 1, completed: 2, failed: -1)
 * String   transactionToken- Mã token của giao dịch thanh toán (zptranstoken)
 * Date     createdAt           - Thời gian tạo hóa đơn
 * Date     updatedAt           - Thời gian cập nhật hóa đơn
 * int      deleted         - Xóa
 * */

public class Bill {
    private String id;
    private String user_id;
    private Price price;
    private String pitch_id;
    private String stadium_id;
    private int status;
    private String transactionToken;
    private String transactionId;
    private int deleted;
    private Date createdAt;
    private Date updatedAt;
    public Bill() {
    }

    public Bill(String id, String user_id, Price price, String pitch_id, String stadium_id, int status, String transactionToken, String transactionId, int deleted, Date createdAt, Date updatedAt) {
        this.id = id;
        this.user_id = user_id;
        this.price = price;
        this.pitch_id = pitch_id;
        this.stadium_id = stadium_id;
        this.status = status;
        this.transactionToken = transactionToken;
        this.transactionId = transactionId;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStadium_id() {
        return stadium_id;
    }

    public void setStadium_id(String stadium_id) {
        this.stadium_id = stadium_id;
    }

    public void stadium(final OnStadiumFetchListener listener) {
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("stadiums").document(stadium_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Stadium stadium = document.toObject(Stadium.class);
                        if (listener != null) {
                            listener.onStadiumFetch(stadium);
                        }
                    }
                }
            }
        });
    }

    public void pitch(final OnPitchFetchListener listener) {
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("pitches").document(pitch_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Pitch pitch = document.toObject(Pitch.class);
                        if (listener != null) {
                            listener.onPitchFetch(pitch);
                        }
                    }
                }
            }
        });
    }

    public interface OnStadiumFetchListener {
        void onStadiumFetch(Stadium stadium);
    }
    public interface OnPitchFetchListener {
        void onPitchFetch(Pitch pitch);
    }
}
