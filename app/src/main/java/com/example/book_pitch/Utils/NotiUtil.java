package com.example.book_pitch.Utils;

import android.content.Context;

import me.leolin.shortcutbadger.ShortcutBadger;

public class NotiUtil {
    public static void setBadge(Context context, int count) {
        // Thiết lập badge trên biểu tượng ứng dụng
        ShortcutBadger.applyCount(context, count);
    }
}
