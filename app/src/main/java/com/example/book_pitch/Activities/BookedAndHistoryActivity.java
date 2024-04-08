package com.example.book_pitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.book_pitch.Adapters.TabViewBookedAdapter;
import com.example.book_pitch.Fragment.AllFragment;
import com.example.book_pitch.Fragment.FailedFragment;
import com.example.book_pitch.Fragment.HistoryBookedFragment;
import com.example.book_pitch.Fragment.PendingFragment;
import com.example.book_pitch.Fragment.PitchBookedFragment;
import com.example.book_pitch.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BookedAndHistoryActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private Fragment fragment = null;
    private TabViewBookedAdapter tabViewBookedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_and_history);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.history_and_booked);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TabLayout mTabLayout = findViewById(R.id.tab_layout);
        fragment = new AllFragment();

        handleTab();

        mTabLayout.addOnTabSelectedListener(this);

    }

    private void handleTab() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.tab_frame, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                fragment = new AllFragment();
                break;
            case 1:
                fragment = new PendingFragment();
                break;
            case 2:
                fragment = new PitchBookedFragment();
                break;
            case 3:
                fragment = new HistoryBookedFragment();
                break;
            case 4:
                fragment = new FailedFragment();
                break;
            default:
                break;
        }

        handleTab();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}