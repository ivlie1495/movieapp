package com.ivlie7.favourite.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ivlie7.favourite.R;
import com.ivlie7.favourite.base.BaseViewHolder;
import com.ivlie7.favourite.constant.ApiConstants;
import com.ivlie7.favourite.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context context;
    private Cursor mCursor;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list, viewGroup, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        Movie movie = getItem(i);
        baseViewHolder.textViewTitle.setText(movie.getTitle());
        baseViewHolder.textViewRelease.setText(movie.getReleaseDate());
        baseViewHolder.textViewRating.setText(String.valueOf(movie.getVoteAverage()));
        Glide.with(context).load(ApiConstants.API_POSTER + movie.getPosterPath()).into(baseViewHolder.imageViewPoster);
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public void setMovie(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    private Movie getItem(int i) {
        if (!mCursor.moveToPosition(i)) {
            throw new IllegalStateException("No Position Found!");
        }

        return new Movie(mCursor);
    }
}
