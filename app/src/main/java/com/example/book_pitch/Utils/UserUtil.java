package com.example.book_pitch.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserUtil {
    private static final String USER_PREFS = "UserPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS= "address";
    private static final String KEY_AVATAR= "avatar";
    private static final String KEY_GENDER= "gender";

    private static SharedPreferences getUserSharedPreferences(Context context) {
        return context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
    }

    public static void setUser(Context context, String userId, String username, String email, String phone, String address, String avatar, String gender) {
        SharedPreferences.Editor editor = getUserSharedPreferences(context).edit();
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_AVATAR, avatar);
        editor.putString(KEY_GENDER, gender);
        editor.apply();
    }

    public static String getUserId(Context context) {
        return getUserSharedPreferences(context).getString(KEY_USER_ID, null);
    }

    public static String getUsername(Context context) {
        return getUserSharedPreferences(context).getString(KEY_USERNAME, null);
    }

    public static String getEmail(Context context) {
        return getUserSharedPreferences(context).getString(KEY_EMAIL, null);
    }

    public static String getPhone(Context context) {
        return getUserSharedPreferences(context).getString(KEY_PHONE, null);
    }
    public static String getAvatar(Context context) {
        return getUserSharedPreferences(context).getString(KEY_AVATAR, null);
    }
    public static String getAddress(Context context) {
        return getUserSharedPreferences(context).getString(KEY_ADDRESS, null);
    }
    public static String getGender(Context context) {
        return getUserSharedPreferences(context).getString(KEY_GENDER, null);
    }
}
