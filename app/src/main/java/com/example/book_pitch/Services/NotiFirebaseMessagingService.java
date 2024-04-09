package com.example.book_pitch.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.book_pitch.Activities.NotificationActivity;
import com.example.book_pitch.Models.Notifi;
import com.example.book_pitch.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotiFirebaseMessagingService extends FirebaseMessagingService {
   /* @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if(notification == null){
            return;
        }
        String title = notification.getTitle();
        String content = notification.getBody();
        sendNotification(title,content);
    }

    private void sendNotification(String title, String content){
      *//*  Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);*//*
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NotificationActivity.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.bell)
                .setAutoCancel(true);
        Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null){
            notificationManager.notify(1,notification);
        }
    }
*/
}

