package com.example.book_pitch.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;

import java.util.ArrayList;
import java.util.List;

public class NearMeAdapter extends BaseAdapter {
    private ArrayList<Stadium> listData;
    private Context ctx;

    public NearMeAdapter(ArrayList<Stadium> listData) {
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            view = View.inflate(parent.getContext(), R.layout.popular_view, null);
        }else view = convertView;

        Stadium stadium = (Stadium) getItem(position);

        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtLocation = (TextView) view.findViewById(R.id.txtLocation);
        TextView txtPhone = (TextView) view.findViewById(R.id.txtPhone);
        TextView openTime = (TextView) view.findViewById(R.id.openTime);
        TextView rating = (TextView) view.findViewById(R.id.rating);

        txtTitle.setText(stadium.getTitle());
        txtLocation.setText(stadium.getLocation().getArea());
        txtPhone.setText(stadium.getPhone());
        openTime.setText(stadium.getOpening_time() + stadium.getClosing_time());
        rating.setText(stadium.getAverage_rating());
        return view;
    }
}
