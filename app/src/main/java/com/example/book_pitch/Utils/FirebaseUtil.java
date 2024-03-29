package com.example.book_pitch.Utils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUtil {
    private static final FirebaseAuth fauth = FirebaseAuth.getInstance();
    private static final FirebaseFirestore fstore = FirebaseFirestore.getInstance();
//    private static final FirebaseAuth fauth = FirebaseAuth.getInstance();
    public static String currentUserId() {
        return fauth.getUid();
    }
    public static boolean isLoggedIn() {
        return currentUserId() != null;
    }
    public static CollectionReference userCollection() {
        return fstore.collection("users");
    }
    public static CollectionReference stadiumCollection() {
        return fstore.collection("stadiums");
    }

    public interface OnDataLoadedListener<T> {
        void onDataLoaded(List<T> data);

        void onError(String errorMessage);
    }

    public static void getAll(String collectionPath, Class<?> dataClass, OnDataLoadedListener onDataLoadedListener) {
        fstore.collection(collectionPath).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete()) {
                    List<Object> dataList = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Object data = document.toObject(dataClass);
                    }
                    onDataLoadedListener.onDataLoaded(dataList);
                }else {
                    onDataLoadedListener.onError("Đã xảy ra lỗi nào đó");
                }
            }
        });
    }
}
