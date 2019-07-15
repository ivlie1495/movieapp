package com.ivlie7.submission.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ivlie7.submission.R;
import com.ivlie7.submission.base.BaseViewHolder;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.ui.DetailActivity;
import com.ivlie7.submission.util.ApiUtils;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
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
        final Movie movie = movieList.get(i);

        baseViewHolder.textViewTitle.setText(movie.getTitle());
        baseViewHolder.textViewRating.setText(String.valueOf(movie.getVoteAverage()));
        baseViewHolder.textViewRelease.setText(movie.getReleaseDate());
        Glide.with(context).load(ApiUtils.API_POSTER + movie.getPosterPath()).into(baseViewHolder.imageViewPoster);

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(context.getString(R.string.data), movie);
                intent.putExtra("isMovie", true);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
