package com.example.book_pitch.Utils;

public class DateUtil {
    public static int convertDateToInt(String date) {
        // Tách chuỗi thành ngày, tháng và năm
        String[] parts = date.split("/");
        int day = Integer.parseInt(parts[0].replaceFirst("^0+(?!$)", ""));
        int month = Integer.parseInt(parts[1].replaceFirst("^0+(?!$)", ""));
        int year = Integer.parseInt(parts[2]);

        // Chuyển đổi ngày, tháng và năm thành số
        int dateInt = (year * 10000) + (month * 100) + day;

        return dateInt;
    }
}
