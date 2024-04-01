package com.example.book_pitch.Adapters;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.AndroidUtil;
import com.example.book_pitch.databinding.PopularViewBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends FirestoreRecyclerAdapter<Stadium, PopularAdapter.ViewHolder> {
    private static final String TAG = "FirestoreAdapter";
    private Context ctx;
    private final PopularAdapterOnClickHandler clickHandler;
//
    public interface PopularAdapterOnClickHandler{
        void onClick(Stadium stadium);
    }

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PopularAdapter(@NonNull FirestoreRecyclerOptions<Stadium> options, PopularAdapterOnClickHandler clickHandler) {
        super(options);
        this.clickHandler = clickHandler;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View view = layoutInflater.inflate(R.layout.popular_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Stadium stadium) {
        holder.txtTitle.setText(stadium.getTitle());
        holder.txtLocation.setText(stadium.getAddress());
        holder.txtPhone.setText(stadium.getPhone());
        holder.openTime.setText(stadium.getOpening_time() + "-" + stadium.getClosing_time());

        Log.d(TAG, "Stadium data at position " + position + ": " + stadium.toString());
    }

    @Override
    public void onDataChanged() {
        // Called each time there is a new query snapshot. You may want to use this method
        // to hide a loading spinner or check for the "no documents" state and update your UI.
        // ...
        Log.d(TAG, "==== ====");
    }

    @Override
    public void onError(@NonNull @NotNull FirebaseFirestoreException e) {
        AndroidUtil.showToast(ctx, "Lỗi rồi.");
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle, txtLocation, txtPhone, openTime;
        public ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            openTime = itemView.findViewById(R.id.openTime);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Stadium s = getItem(position);
                    clickHandler.onClick(s);
                }
            });
        }
    }
}
