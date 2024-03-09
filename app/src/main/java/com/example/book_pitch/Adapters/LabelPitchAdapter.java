package com.example.book_pitch.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Models.Pitch;
import com.example.book_pitch.R;

import java.util.List;

public class LabelPitchAdapter extends RecyclerView.Adapter<LabelPitchAdapter.ViewHolder> {
    private List<Pitch> pitches;
    private int selectedItem = RecyclerView.NO_POSITION;

    public LabelPitchAdapter(List<Pitch> pitches) {
        this.pitches = pitches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pitch_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Pitch pitch = pitches.get(position);
        holder.textView.setText(pitch.getLabel());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });

        if(selectedItem == position){
            holder.cardView.setBackgroundColor(Color.parseColor("#85C240"));
            holder.textView.setTextColor(Color.WHITE);
        }else{
            holder.cardView.setBackgroundColor(Color.WHITE);
            holder.textView.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return pitches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.position_pitch);
            cardView = itemView.findViewById(R.id.card_label_pitch);
        }
    }
}
