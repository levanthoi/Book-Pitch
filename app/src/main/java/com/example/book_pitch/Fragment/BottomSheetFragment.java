package com.example.book_pitch.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Adapters.LabelPitchAdapter;
import com.example.book_pitch.Models.Pitch;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.ScreenUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.List;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    private List<Pitch> pitches;
    private Button btnDate;
    private DatePickerDialog datePickerDialog;
    private Context ctx;

    public BottomSheetFragment(Context context, List<Pitch> pitches) {
        this.pitches = pitches;
        this.ctx = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet, null);

        bottomSheetDialog.setContentView(view);
//        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

//        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) view.getParent());
//        ScreenUtils screenUtils=new ScreenUtils(getActivity());
//        mBehavior.setPeekHeight(screenUtils.getHeight());

        render(view);
        initDatePicker();
        btnDate = view.findViewById(R.id.select_date);
        btnDate.setText(getTodayDate());
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        return bottomSheetDialog;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month +=1;
                String date = makeDateString(dayOfMonth, month, year);
                btnDate.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

//        int style = android.R.style.Theme_Material_Light_Dialog_Alert;
        datePickerDialog = new DatePickerDialog(ctx, dateSetListener,year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return dayOfMonth + "/" + month + "/" + year;
    }

    private String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month +=1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month,year);
    }


//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
//
//        render(view);
//        return view;
//    }

    private void render(View view){
        RecyclerView rcl_label_pitch, rcl_durations;
        rcl_label_pitch = view.findViewById(R.id.rcl_label_pitch);
        rcl_label_pitch.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rcl_label_pitch.setAdapter(new LabelPitchAdapter(pitches));

        rcl_durations = view.findViewById(R.id.rcl_durations);
        rcl_durations.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rcl_durations.setAdapter(new LabelPitchAdapter(pitches));

    }
}
