package com.example.book_pitch.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.book_pitch.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetLocation extends BottomSheetFragment{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_location, null);

        bottomSheetDialog.setContentView(view);
        init(view);
        return bottomSheetDialog;
    }
    private void init(View view) {

    }

}
