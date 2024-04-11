package com.example.book_pitch.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.book_pitch.Activities.ReviewActivity;
import com.example.book_pitch.Fragment.BottomSheetHotline;
import com.example.book_pitch.Models.Bill;
import com.example.book_pitch.Models.Pitch;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PitchBookedAdapter extends RecyclerView.Adapter<PitchBookedAdapter.ViewHolder> {
    private List<Bill> bills;
    private Context ctx;
    private final PitchBookedAdapterOnClickHandler clickHandler;
    FirebaseFirestore firestore;

    public interface PitchBookedAdapterOnClickHandler {
        void onClick(Bill bill);
    }

    public PitchBookedAdapter(List<Bill> bills, PitchBookedAdapterOnClickHandler clickHandler) {
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
        private TextView tvTitle, tvAddress, tvNamePitch, tvBeginTime;
        private RatingBar rating_tab;
        private Button btn_primary;
        private ShapeableImageView avatar;
//        private MaterialButton btn_contact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvNamePitch = itemView.findViewById(R.id.tvNamePitch);
            tvBeginTime = itemView.findViewById(R.id.tvBeginTime);
            rating_tab = itemView.findViewById(R.id.rating_tab);
            btn_primary = itemView.findViewById(R.id.btn_primary);
            avatar = itemView.findViewById(R.id.avatar_stadium);
            firestore = FirebaseFirestore.getInstance();
//            btn_contact = itemView.findViewById(R.id.btn_contact);


            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Bill bill = bills.get(position);
                    clickHandler.onClick(bill);
                }
            });
        }
        public void bind(Bill bill) {
            if(bill != null ){
                bill.stadium(new Bill.OnStadiumFetchListener() {
                    @Override
                    public void onStadiumFetch(Stadium stadium) {
                        tvTitle.setText(stadium.getTitle());
                        tvAddress.setText(stadium.getAddress());
                        if (stadium.getAvatar() != null) {
                            Glide.with(ctx).load(stadium.getAvatar()).into(avatar);
                        }
                        rating_tab.setRating(Float.valueOf(stadium.getAverage_rating()));

//                        btn_contact.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                openBottomSheetContact(stadium);
//                            }
//                        });
                    }
                });

                firestore.collection("review").whereEqualTo("bill_id", bill.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
//                            btn_primary.setText("Xem");
                        }
                    }
                });

                bill.pitch(new Bill.OnPitchFetchListener() {
                    @Override
                    public void onPitchFetch(Pitch pitch) {
                        tvNamePitch.setText("Sân " + pitch.getPitch_size() + " - " + "1");
                    }
                });

                btn_primary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ctx, ReviewActivity.class);
                        intent.putExtra("id", bill.getId());
                        ctx.startActivity(intent);
                    }
                });


                tvBeginTime.setText(bill.getPrice().getFrom_time() + " - " + bill.getPrice().getTo_time() + ", "+ bill.getPrice().getTo_date());
            }
        }
    }

//    private void openBottomSheetContact(Stadium stadium) {
//        BottomSheetHotline bottomSheetHotline = new BottomSheetHotline(this, stadium);
//        bottomSheetHotline.show(getSupportFragmentManager(), bottomSheetHotline.getTag());
//    }

}
