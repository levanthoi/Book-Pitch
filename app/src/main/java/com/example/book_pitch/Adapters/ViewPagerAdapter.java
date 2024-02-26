package com.example.book_pitch.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.book_pitch.Fragment.HistoryBookedFragment;
import com.example.book_pitch.Fragment.PitchBookedFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PitchBookedFragment();
            case 1:
                return new HistoryBookedFragment();
            default:
                return new PitchBookedFragment();
        }
    }

    @Override
    public int getItemCount() {

        return 2;
    }
}
