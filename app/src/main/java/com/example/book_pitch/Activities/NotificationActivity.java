package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.book_pitch.Adapters.NotificationAdapter;
import com.example.book_pitch.Models.Notification;
import com.example.book_pitch.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView rcvNoti_new, rcvNoti_old;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Thông báo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rcvNoti_new = findViewById(R.id.rv_noti_new);
        rcvNoti_old = findViewById(R.id.rv_noti_old);

        rcvNoti_new.setLayoutManager(new LinearLayoutManager(this));
        NotificationAdapter noti_newAdapter = new NotificationAdapter(getNotiList());
        rcvNoti_new.setAdapter(noti_newAdapter);

        rcvNoti_old.setLayoutManager(new LinearLayoutManager(this));
        NotificationAdapter noti_oldAdapter = new NotificationAdapter(getNotiList1());
        rcvNoti_old.setAdapter(noti_oldAdapter);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // Kiểm tra xem nút back đã được nhấn chưa
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
}
