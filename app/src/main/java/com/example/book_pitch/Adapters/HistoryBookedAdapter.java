package com.example.book_pitch.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Models.Bill;
import com.example.book_pitch.Models.Pitch;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;

import java.util.ArrayList;

public class HistoryBookedAdapter extends RecyclerView.Adapter<HistoryBookedAdapter.ViewHolder> {
    private ArrayList<Bill> bills;
    private Context ctx;
    private final HistoryBookedAdapterOnClickHandler clickHandler;

    public interface HistoryBookedAdapterOnClickHandler {
        void onClick(Bill bill);
    }

    public HistoryBookedAdapter(ArrayList<Bill> bills, HistoryBookedAdapterOnClickHandler clickHandler) {
        this.bills = bills;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_history_booked, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bill bill = bills.get(position);
        holder.bind(bill);
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvAddress, tvBeginTime, tvNamePitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvBeginTime = itemView.findViewById(R.id.tvBeginTime);
            tvNamePitch = itemView.findViewById(R.id.tvNamePitch);
            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Bill bill = bills.get(position);
                    clickHandler.onClick(bill);
                }
            });
        }
        public void bind(Bill bill) {
            Log.d("test", bill.toString());
            bill.stadium(new Bill.OnStadiumFetchListener() {
                @Override
                public void onStadiumFetch(Stadium stadium) {
                    tvTitle.setText(stadium.getTitle());
                    tvAddress.setText(stadium.getAddress());
                }
            });

            bill.pitch(new Bill.OnPitchFetchListener() {
                @Override
                public void onPitchFetch(Pitch pitch) {
                    tvNamePitch.setText("Sân " + pitch.getPitch_size() + " - " + "1");
                }
            });


            tvBeginTime.setText(bill.getPrice().getFrom_time() + " - " + bill.getPrice().getTo_time() + ", "+ bill.getPrice().getTo_date());
        }
    }
}
