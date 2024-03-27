package com.example.book_pitch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.R;

public class IntroViewAdapter extends RecyclerView.Adapter<IntroViewAdapter.IntroViewHolder> {
    private int[] mLayouts;
    private View mFirstLayoutView;
    public IntroViewAdapter(int[] layouts) {
        this.mLayouts = layouts;
    }

    @NonNull
    @Override
    public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        mFirstLayoutView = inflater.inflate(mLayouts[0], parent, false);
        return new IntroViewHolder(mFirstLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewHolder holder, int position) {
        holder.bindLayout(mLayouts[position]);
    }

    @Override
    public int getItemCount() {
        return mLayouts.length;
    }

    public class IntroViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public IntroViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
        public void bindLayout(int layoutResId) {
            ViewGroup itemViewGroup = (ViewGroup) itemView;
            if (itemViewGroup.getChildCount() > 0) {
                itemViewGroup.removeAllViews(); // Xóa bất kỳ view con nào nếu đã có
            }
            LayoutInflater.from(itemViewGroup.getContext()).inflate(layoutResId, itemViewGroup, true);
        }
    }

}
