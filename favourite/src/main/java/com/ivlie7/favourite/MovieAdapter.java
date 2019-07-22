package com.ivlie7.favourite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final ArrayList<Movie> movies = new ArrayList<>();
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list, viewGroup, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        Movie movie = movies.get(i);
        baseViewHolder.textViewTitle.setText(movie.getTitle());
        baseViewHolder.textViewRelease.setText(movie.getReleaseDate());
        baseViewHolder.textViewRating.setText(String.valueOf(movie.getVoteAverage()));
//        Glide.with(context).load(ApiConstants.API_POSTER + movie.getPosterPath()).into(baseViewHolder.imageViewPoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
