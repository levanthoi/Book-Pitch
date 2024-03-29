package com.example.book_pitch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class GenderAdapter extends ArrayAdapter<String> {

    private List<String> genders;
    private LayoutInflater inflater;

    public GenderAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, android.R.layout.simple_spinner_item, objects);
        this.genders = objects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return genders.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        String gender = genders.get(position);

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(gender);

        return view;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        String gender = genders.get(position);

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(gender);

        return view;
    }
}
