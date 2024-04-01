package com.example.book_pitch.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.book_pitch.Adapters.NewsAdapter;
import com.example.book_pitch.Adapters.NotificationAdapter;
import com.example.book_pitch.Models.News;
import com.example.book_pitch.Models.Notification;
import com.example.book_pitch.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "push_notification_id";
    RecyclerView rcvNoti_new, rcvNoti_old;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        rcvNoti_new = findViewById(R.id.rv_noti_new);
        rcvNoti_old = findViewById(R.id.rv_noti_old);

        rcvNoti_new.setLayoutManager(new LinearLayoutManager(this));
        NotificationAdapter noti_newAdapter = new NotificationAdapter(getNotiList());
        rcvNoti_new.setAdapter(noti_newAdapter);

        rcvNoti_old.setLayoutManager(new LinearLayoutManager(this));
        NotificationAdapter noti_oldAdapter = new NotificationAdapter(getNotiList1());
        rcvNoti_old.setAdapter(noti_oldAdapter);
        createNotification();

//        getNotification();

    }

    private List<Notification> getNotiList() {
        List<Notification> list = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            Notification noti = new Notification(i + "Các sân còn trống! Bạn có thể tham khảo?", "https://s.yimg.com/fz/api/res/1.2/JmKnAYUB3x_xe4e1_USlXQ--~C/YXBwaWQ9c3JjaGRkO2ZpPWZpbGw7aD0zODQ7cT04MDt3PTUxMg--/https://www.bing.com/th?id=OVFT.qTPdK1nejgowIFQaD6485C&pid=News&w=700&h=365&c=14&qlt=90&dpr=2", "15 phút trước", "Sân trường Đại học Thủy Lợi");
            list.add(noti);
        }
        return list;

    }

    private List<Notification> getNotiList1() {
        List<Notification> list1 = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Notification noti1 = new Notification(i + "Các sân còn trống! Bạn có thể tham khảo?", "https://s.yimg.com/fz/api/res/1.2/JmKnAYUB3x_xe4e1_USlXQ--~C/YXBwaWQ9c3JjaGRkO2ZpPWZpbGw7aD0zODQ7cT04MDt3PTUxMg--/https://www.bing.com/th?id=OVFT.qTPdK1nejgowIFQaD6485C&pid=News&w=700&h=365&c=14&qlt=90&dpr=2", "15 phút trước", "Sân trường Đại học Thủy Lợi");
            list1.add(noti1);
        }
        return list1;
    }

    public void createNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "PushNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
//    void getNotification(){
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
//            if(task.isSuccessful()){
//                String token = task.getResult();
//                Log.i("My token", token);
//            }
//        });
//    }

}
