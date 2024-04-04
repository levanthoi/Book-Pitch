package com.example.book_pitch.Services;

import com.example.book_pitch.Models.Bill;
import com.example.book_pitch.Utils.FirebaseUtil;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BillService {
    private FirebaseFirestore firestore ;

    public BillService() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public void createBill() {
        final String auth_id = FirebaseUtil.currentUserId();

        Bill bill = new Bill();
        DocumentReference ref = firestore.collection("bills").document(auth_id);
    }
}
