package com.example.book_pitch.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.book_pitch.Activities.MainActivity;
import com.example.book_pitch.Activities.NotificationActivity;
import com.example.book_pitch.Fragment.HomeFragment;
import com.example.book_pitch.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificatonService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //Nhận data
        RemoteMessage.Notification notification =  remoteMessage.getNotification();
            if(notification == null){
            return;
        }
        String strTitle = notification.getTitle();
        String strContent = notification.getBody();

        sendNotification(strTitle, strContent);
        
    }
    public void sendNotification(String strTitle, String strContent) {

        Intent  intent = new Intent(this, HomeFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);;
        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this, NotificationActivity.CHANNEL_ID)
                .setContentTitle(strTitle)
                .setContentText(strContent)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        Notification notification = notiBuilder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null){
            notificationManager.notify(1, notification);
        }

    }
}
