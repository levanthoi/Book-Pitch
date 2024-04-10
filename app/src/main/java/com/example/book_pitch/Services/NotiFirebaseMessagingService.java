package com.example.book_pitch.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.book_pitch.Activities.NotificationActivity;
import com.example.book_pitch.Models.Notifi;
import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class NotiFirebaseMessagingService extends FirebaseMessagingService {
    private AtomicInteger id = new AtomicInteger(0);
   @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if(notification == null){
            return;
        }
        String title = notification.getTitle();
        String content = notification.getBody();
        sendNotification(title,content);
        addNotificationToFirestore(title, content);
    }

    private void sendNotification(String title, String content){
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NotificationActivity.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.bell)
                .setContentIntent(pendingIntent);

        Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null){
            notificationManager.notify(1,notification);
        }
    }

    private void addNotificationToFirestore(String title, String content) {
        // Khởi tạo Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> notifications = new HashMap<>();
        notifications.put("title", title);
        notifications.put("content", content);
        notifications.put("notificationsType", "notificationSystem");
        // Thêm dữ liệu vào Firestore
        db.collection("notifications")
                .document(String.valueOf(id.getAndIncrement()))
                .set(notifications)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(NotiFirebaseMessagingService.this, "Thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

