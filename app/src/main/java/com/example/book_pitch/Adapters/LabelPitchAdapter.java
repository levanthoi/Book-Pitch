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
    private LabelPitchClickListener listener;

    public interface LabelPitchClickListener {
        void onClick(Pitch pitch);
    }

    public LabelPitchAdapter(List<Pitch> pitches, LabelPitchClickListener listener) {
        this.pitches = pitches;
        this.listener = listener;
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
        holder.textView.setText("Sân " + pitch.getPitch_size()+ " - " + pitch.getLabel());

        holder.itemView.setSelected(selectedItem == position);

        if(selectedItem == position){
            holder.cardView.setBackgroundColor(Color.parseColor("#85C240"));
            holder.cardView.setBackgroundResource(R.drawable.border_cardview);
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

            cardView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    selectedItem = position;
                    listener.onClick(pitches.get(position));
                    // Cập nhật thay đổi
                    notifyDataSetChanged();
                }
            });
        }
    }
}
