package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.SystemClock;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.book_pitch.Adapters.NotificationAdapter;
import com.example.book_pitch.Models.Notifi;
import com.example.book_pitch.R;
import com.example.book_pitch.Services.NotiDataMessagingService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView rcvNoti_new, rcvNoti_old;

    private List<Notifi> notificationList;

    NotificationAdapter noti_newAdapter, noti_oldAdapter;


    public static final String CHANNEL_ID = "push_notification";

    private FirebaseFirestore fireStore;
    private FirebaseMessaging fireMessaging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        fireStore = FirebaseFirestore.getInstance();
        createNotification();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thông báo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        notificationList = new ArrayList<>();

        rcvNoti_new = findViewById(R.id.rv_noti_new);
        rcvNoti_old = findViewById(R.id.rv_noti_old);

        rcvNoti_new.setLayoutManager(new LinearLayoutManager(this));
        noti_newAdapter = new NotificationAdapter(notificationList);
        rcvNoti_new.setAdapter(noti_newAdapter);

        rcvNoti_old.setLayoutManager(new LinearLayoutManager(this));
        noti_oldAdapter = new NotificationAdapter(notificationList);
        rcvNoti_old.setAdapter(noti_oldAdapter);

    /*    Intent intent = new Intent(this, NotiDataMessagingService.class);
        startService(intent);*/
        scheduleNotification();

        fetchDataFromFirestore();
    }

    private void scheduleNotification() {
        // Tạo một Intent để gửi thông báo
        Intent notificationIntent = new Intent(this, NotiDataMessagingService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE);

        // Lên lịch gửi thông báo mỗi 24 giờ
        long futureInMillis = SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

        private void fetchDataFromFirestore () {
            fireStore.collection("notification")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Lấy dữ liệu từ mỗi document và thêm vào danh sách
                                    String title = document.getString("title");
                                    String content = document.getString("content");
                                    notificationList.add(new Notifi(title,content));
                                    /*Notifi noti = document.toObject(Notifi.class);
                                    notificationList.add(noti);*/
                                }
                                // Cập nhật Adapter khi đã lấy được dữ liệu mới
                                noti_newAdapter.notifyDataSetChanged();
                                noti_oldAdapter.notifyDataSetChanged();

                            } else {
                                Log.d("MainActivity", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }





    private void createNotification(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.S) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "PushNotification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // Kiểm tra xem nút back đã được nhấn chưa
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


/*    private List<Notification> getNotiList() {
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
    }*/

}
