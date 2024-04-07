package com.example.book_pitch.Utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.book_pitch.Models.Bill;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QrCodeUtil {
    public static Bitmap generateQRCode(Bill bill, int width, int height) {
        String jsonData = convertBillToJson(bill);
        return createQRCode(jsonData, width, height);
    }

    private static String convertBillToJson(Bill bill) {
        Gson gson = new Gson();
        return gson.toJson(bill);
    }

    private static Bitmap createQRCode(String data, int width, int height) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    data, BarcodeFormat.QR_CODE, width, height);

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
