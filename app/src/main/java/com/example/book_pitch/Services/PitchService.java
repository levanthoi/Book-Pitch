package com.example.book_pitch.Services;

import androidx.annotation.NonNull;

import com.example.book_pitch.Models.Pitch;
import com.example.book_pitch.Utils.AndroidUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PitchService {
    private FirebaseFirestore firestore =  FirebaseFirestore.getInstance();

    public interface OnPitchesLoadedListener {
        void onPitchesLoaded(List<Pitch> pitches);
    }



    public void getAllPitchesForStadium(String stadium_id, final OnPitchesLoadedListener listener) {
        firestore.collection("pitches")
                .whereEqualTo("stadium_id", stadium_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Pitch> pitches = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Pitch p = document.toObject(Pitch.class);
                                pitches.add(p);
                            }
                            if (listener != null) {
                                listener.onPitchesLoaded(pitches);
                            }
                        } else {
//                            Log.e("PitchService", "Error getting pitches", task.getException());
                            if (listener != null) {
                                listener.onPitchesLoaded(null);
                            }
                        }
                    }
                });
    }


    public DocumentSnapshot getOnePitch(String pitchId) {
        try{
            Task<DocumentSnapshot> task = firestore.collection("pitches")
                    .document(pitchId)
                    .get();
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
                return document;
            } else {
                throw new Exception("Pitch not found");
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
