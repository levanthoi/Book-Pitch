package com.example.book_pitch.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.book_pitch.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class UserUtil {
    private static final String USER_PREFS = "UserPrefs";
    private static final String KEY_USER = "user";

    private static SharedPreferences getUserSharedPreferences(Context context) {
        return context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
    }

    public static void setUser(Context context, User user) {
        SharedPreferences.Editor editor = getUserSharedPreferences(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(KEY_USER, json);
        editor.apply();
    }

    public static User getUser(Context context) {
        SharedPreferences sharedPreferences = getUserSharedPreferences(context);
        String json = sharedPreferences.getString(KEY_USER, null);
        Gson gson = new Gson();
        if(json == null) storeUser(context);
        return gson.fromJson(json, User.class);
    }

    public static void storeUser(Context context) {
        FirebaseFirestore.getInstance().collection("users").document(FirebaseUtil.currentUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    setUser(context, doc.toObject(User.class));
                }
            }
        });
    }
}
