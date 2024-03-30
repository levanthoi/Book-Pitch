package com.example.book_pitch.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Models.Price;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.Helper;

import java.util.List;

public class ShowPitchAdapter extends RecyclerView.Adapter<ShowPitchAdapter.ViewHolder>{
    List<Price> prices;
    private int selectedItem = RecyclerView.NO_POSITION;
    private ShowPitchClickListener listener;
    public interface ShowPitchClickListener{
        void onClickShowPitch(Price price);
    }

    public ShowPitchAdapter(List<Price> prices, ShowPitchClickListener listener) {
        this.prices = prices;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_pitch, parent, false);
        return new ShowPitchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Price price = prices.get(position);
        holder.show_time.setText(price.getFrom_time() + "-" + price.getTo_time());
        holder.show_price.setText(Helper.formatNumber(price.getPrice()));

        holder.itemView.setSelected(selectedItem == position);

        if(selectedItem == position){
            holder.cardView.setBackgroundColor(Color.parseColor("#85C240"));
            holder.cardView.setBackgroundResource(R.drawable.border_cardview);
            holder.show_time.setTextColor(Color.WHITE);
            holder.show_price.setTextColor(Color.WHITE);
        }else{
            holder.cardView.setBackgroundColor(Color.WHITE);
            holder.show_time.setTextColor(Color.BLACK);
            holder.show_price.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return prices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView show_time, show_price;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_time = itemView.findViewById(R.id.show_time);
            show_price = itemView.findViewById(R.id.show_price);
            cardView = itemView.findViewById(R.id.card_show_pitch);

            cardView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    selectedItem = position;
                    listener.onClickShowPitch(prices.get(position));
                    // Cập nhật thay đổi
                    notifyDataSetChanged();
                }
            });
        }
    }
}
