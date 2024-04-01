package com.example.book_pitch.Utils;

import java.text.NumberFormat;
import java.util.Locale;

public class Helper {
    public static String formatPrice(double price) {
        // Tạo một đối tượng NumberFormat
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.getDefault());

        // Định dạng giá số
        String formattedPrice = formatter.format(price);

        return formattedPrice;
    }

    public static String formatNumber(String value) {
        if (value == null || value.isEmpty()) {
            return "0";
        }

        String[] list = value.split("\\.");
        String prefix = list[0].charAt(0) == '-' ? "-" : "";
        String num = prefix.isEmpty() ? list[0] : list[0].substring(1);
        String result = "";

        while (num.length() > 3) {
            result = "," + num.substring(num.length() - 3) + result;
            num = num.substring(0, num.length() - 3);
        }

        if (!num.isEmpty()) {
            result = num + result;
        }

        return prefix + result + (list.length > 1 ? "." + list[1] : "");
    }
}
