package com.example.book_pitch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_pitch.Models.User;
import com.example.book_pitch.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SearchUserAdapter extends FirestoreRecyclerAdapter<User,SearchUserAdapter.ViewHolder> {


    private Context ctx;
    private final SearchUserAdapter.SearchUserAdapterOnClickHandler clickHandler;
    public interface SearchUserAdapterOnClickHandler{
        void onClick(User user);
    }

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     * @param clickHandler
     */
    public SearchUserAdapter(@NonNull FirestoreRecyclerOptions<User> options, SearchUserAdapter.SearchUserAdapterOnClickHandler clickHandler) {
        super(options);
        this.clickHandler = clickHandler;
    }

    @Override
    protected void onBindViewHolder(@NonNull SearchUserAdapter.ViewHolder holder, int position, @NonNull User model) {
//        holder.phone_text.
    }

    @NonNull
    @Override
    public SearchUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View view = layoutInflater.inflate(R.layout.item_group_messages, parent, false);
        return new SearchUserAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
