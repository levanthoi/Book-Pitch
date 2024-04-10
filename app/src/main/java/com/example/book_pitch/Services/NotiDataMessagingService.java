package com.example.book_pitch.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.book_pitch.Activities.NotificationActivity;
import com.example.book_pitch.Models.Notifi;
import com.example.book_pitch.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class NotiDataMessagingService extends BroadcastReceiver {
    private static int notificationId = 0;
    public static final String CHANNEL_ID = "push_notification";

    private FirebaseFirestore db;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();

        // Lưu dữ liệu thông báo vào Firestore
        saveNotificationToFirestore(context);
    }

    private void saveNotificationToFirestore(Context context) {
        // Lấy dữ liệu từ thông báo, ví dụ:
        String title = "Quan trọng";
        String content = "Các sân còn trống";

        // Lưu dữ liệu vào Firestore
        Notifi noti = new Notifi(title, content);
        db.collection("notification")
                .add(noti)
                .addOnSuccessListener(documentReference -> {
                    // Thành công
                    // Sau khi lưu vào Firestore, bạn có thể thông báo cho người dùng bằng cách gửi thông báo hoặc cập nhật RecyclerView
                    sendNotification(context, title, content);
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi gặp lỗi
                });
    }

    private void sendNotification(Context context, String title, String content) {

        notificationId++;

       /* Intent intent = new Intent(context, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE);
*/
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NotificationActivity.CHANNEL_ID)
                .setContentTitle("Thông báo" + title)
                .setContentText(content)
                .setSmallIcon(R.drawable.bell)
                .setAutoCancel(true);
        Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "PushNotification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        if (notificationManager != null) {
            notificationManager.notify(notificationId, notification);
        }
    }
}

  /* private String TAG= "Notificaton";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            // Lấy dữ liệu từ thông báo
            String title = remoteMessage.getData().get("title");
            String content = remoteMessage.getData().get("content");

            // Tạo một đối tượng CampaignMessage từ dữ liệu
            Notifi noti = new Notifi(title, content);

            // Gửi thông báo đến Activity để cập nhật RecyclerView
           *//* *newsAdapter.addData(noti);*//*
            saveDataToFirestore(title, content);
        }
    }
    private void saveDataToFirestore(String title, String content) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Notifi noti = new Notifi(title, content);
        db.collection("notification")
                .add(noti)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding document", e);
                });
    }
    }*/
       /* data.out
        Intent intent = new Intent("ACTION_UPDATE_RECYCLER_VIEW");
        intent.putExtra("campaign_message", String.valueOf(noti));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);*/

