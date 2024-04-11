package com.example.book_pitch.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Activities.LoginPhoneNumberActivity;
import com.example.book_pitch.Activities.PaymentActivity;
import com.example.book_pitch.Adapters.DurationAdapter;
import com.example.book_pitch.Adapters.LabelPitchAdapter;
import com.example.book_pitch.Adapters.ShowPitchAdapter;
import com.example.book_pitch.Models.Bill;
import com.example.book_pitch.Models.Pitch;
import com.example.book_pitch.Models.Price;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.AndroidUtil;
import com.example.book_pitch.Utils.DateUtil;
import com.example.book_pitch.Utils.FirebaseUtil;
import com.example.book_pitch.Utils.ScreenUtils;
import com.example.book_pitch.Utils.TimeUtil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BottomSheetFragment extends BottomSheetDialogFragment implements LabelPitchAdapter.LabelPitchClickListener, DurationAdapter.DurationClickListener, ShowPitchAdapter.ShowPitchClickListener {
    RecyclerView rcl_label_pitch, rcl_durations, rcl_show_pitch;
    private List<Pitch> pitches;
    private Button btnDate, btnBooking;
    private DatePickerDialog datePickerDialog;
    private Context ctx;
    private Pitch pitch_selected;
    private int duration_selected = 0;
    private String date;
    private Stadium stadium;
    private Price newPrice = new Price();
//    private List<Price> results;

    public BottomSheetFragment() {
    }

    public BottomSheetFragment(Context context, List<Pitch> pitches, Stadium stadium) {
        this.pitches = pitches;
        this.ctx = context;
        this.stadium = stadium;
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
        date = getTodayDate();
        btnDate.setText(date);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

//        results = new ArrayList<>();
        rcl_show_pitch = view.findViewById(R.id.rcl_show_pitch);
        rcl_show_pitch.setLayoutManager(new GridLayoutManager(getContext(), 3));
        return bottomSheetDialog;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                date = makeDateString(dayOfMonth, month, year);
                btnDate.setText(date);
//                newPrice.setFrom_date(date);
//                newPrice.setTo_date(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

//        int style = android.R.style.Theme_Material_Light_Dialog_Alert;
        datePickerDialog = new DatePickerDialog(ctx, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return dayOfMonth + "/" + month + "/" + year;
    }

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month += 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }


    private void render(View view) {
        rcl_label_pitch = view.findViewById(R.id.rcl_label_pitch);
        rcl_label_pitch.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rcl_label_pitch.setAdapter(new LabelPitchAdapter(pitches, this));

        rcl_durations = view.findViewById(R.id.rcl_durations);
        rcl_durations.setLayoutManager(new GridLayoutManager(getContext(), 3));

        btnBooking = view.findViewById(R.id.buyButton);
        btnBooking.setVisibility(View.GONE);

        if(!FirebaseUtil.isLoggedIn()){
            btnBooking.setText("Đăng nhập để đặt sân");
        }else btnBooking.setText("Đặt Ngay");

    }

    @Override
    public void onClick(Pitch pitch) {
        pitch_selected = pitch;
        rcl_durations.setAdapter(new DurationAdapter(pitch.getDurations(), this::onClickDuration));

        // set lại giá trị cho duration_selected khi không chọn
//        duration_selected = 0;
//        btnBooking.setVisibility(View.GONE);

        // list giá các khung giờ
//        if(results.size()>0) {
//            results.clear();
//            rcl_show_pitch.getAdapter().notifyDataSetChanged();
//        }

    }

    @Override
    public void onClickDuration(int duration) {
        duration_selected = duration;
        List<Price> results = new ArrayList<>();
        if(pitch_selected != null && date != null)
            for (Price price : pitch_selected.getPrices()) {
                if ((price.getDuration() == duration) && inDate(price)) {
                    int limit = TimeUtil.convertTimeToInt(price.getTo_time());
                    for(int i = TimeUtil.convertTimeToInt(price.getFrom_time()); i<=limit; i+=duration) {
                        Price newPrice = new Price();
                        newPrice.setId(price.getId());
                        newPrice.setDuration(price.getDuration());
                        newPrice.setFrom_date(date);
                        newPrice.setTo_date(date);
                        newPrice.setFrom_time(TimeUtil.convertIntToTime(i));
                        newPrice.setTo_time(TimeUtil.convertIntToTime(i + duration));
                        newPrice.setPitch_id(pitch_selected.getId());
                        newPrice.setPrice(price.getPrice());

                        results.add(newPrice);
                    }
                }
        }

//        Log.d("test", results.toString());

        rcl_show_pitch.setAdapter(new ShowPitchAdapter(results, this::onClickShowPitch));
    }

    private boolean inDate(Price price) {
        if (DateUtil.convertDateToInt(price.getFrom_date()) <= DateUtil.convertDateToInt(date)
                && DateUtil.convertDateToInt(price.getTo_date()) >= DateUtil.convertDateToInt(date)) {
            return true;
        }
        return false;
    }

    @Override
    public void onClickShowPitch(Price price) {
        if(duration_selected != 0){
            btnBooking.setVisibility(View.VISIBLE);
            btnBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!FirebaseUtil.isLoggedIn()){
                        Intent intent = new Intent(getActivity(), LoginPhoneNumberActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getActivity(), PaymentActivity.class);
                        if(pitch_selected != null && stadium != null) {
                            price.setFrom_date(date);
                            price.setTo_date(date);
                            Gson gson = new Gson();
                            intent.putExtra("stadium", gson.toJson(stadium));
                            intent.putExtra("pitch", gson.toJson(pitch_selected));
                            intent.putExtra("price", gson.toJson(price));
                            startActivity(intent);
                        }else{
                            AndroidUtil.showToast(getContext(), "Đang lỗi hệ thống. Vui lòng khởi động lại");
                        }
                    }
                    dismiss();
                }
            });
        }else {
            btnBooking.setVisibility(View.GONE);
        }
    }
}
