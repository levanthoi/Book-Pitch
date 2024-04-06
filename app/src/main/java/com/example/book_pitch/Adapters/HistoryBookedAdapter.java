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
        private TextView tvName, location, tvPhoneNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            location = itemView.findViewById(R.id.location);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Bill bill = bills.get(position);
                    clickHandler.onClick(bill);
                }
            });
        }
        public void bind(Bill bill) {
//            tvName.setText(bill.getTitle());
//            location.setText(bill.getAddress());
//            tvPhoneNumber.setText(bill.getPhone());
        }
    }
}
