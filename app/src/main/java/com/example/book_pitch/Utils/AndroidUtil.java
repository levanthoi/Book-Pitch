package com.example.book_pitch.Utils;

import android.content.Context;
import android.widget.Toast;

public class AndroidUtil {
    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
