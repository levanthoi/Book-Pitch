package com.example.book_pitch.Utils;

public class TimeUtil {
    public static int convertTimeToInt(String time) {
        // Tách chuỗi thành giờ và phút
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0].replaceFirst("^0+(?!$)", ""));
        int minute = Integer.parseInt(parts[1].replaceFirst("^0+(?!$)", ""));

        // Chuyển đổi giờ và phút thành số
        int timeInt = (hour * 60) + minute;

        return timeInt;
    }

    public static String convertIntToTime(int timeInt) {
        // Tách giờ và phút từ số nguyên
        int hour = timeInt / 60;
        int minute = timeInt % 60;

        if(minute >= 60) {
            hour += minute/60;
            minute %= 60;
        }

        // Tạo chuỗi định dạng "hh:mm"
        String timeString = String.format("%02d:%02d", hour, minute);
        return timeString;
    }
}
