package com.example.book_pitch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Models.Bill;
import com.example.book_pitch.Models.Favourite;
import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PitchBookedAdapter extends RecyclerView.Adapter<PitchBookedAdapter.ViewHolder> {
    private ArrayList<Bill> bills;
    private Context ctx;
    private final PitchBookedAdapterOnClickHandler clickHandler;

    public interface PitchBookedAdapterOnClickHandler {
        void onClick(Bill bill);
    }

    public PitchBookedAdapter(ArrayList<Bill> bills, PitchBookedAdapterOnClickHandler clickHandler) {
        this.bills = bills;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_pitch_booked, parent, false);
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
        private TextView tvTitle, tvAddress, tvNamePitch, tvBeginTime, tvEndTime, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvNamePitch = itemView.findViewById(R.id.tvNamePitch);
            tvBeginTime = itemView.findViewById(R.id.tvBeginTime);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
            tvDate = itemView.findViewById(R.id.tvDate);


            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Bill bill = bills.get(position);
                    clickHandler.onClick(bill);
                }
            });
        }
        public void bind(Bill bill) {
            tvTitle.setText(bill.getName());
            tvAddress.setText(bill.getAddress());
            tvNamePitch.setText(bill.getName());
            tvBeginTime.setText(bill.getBeginTime());
            tvEndTime.setText(bill.getEndTime());
            tvDate.setText(bill.getDate());
        }
    }
}
