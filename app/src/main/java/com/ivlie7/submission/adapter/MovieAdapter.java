package com.ivlie7.submission.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ivlie7.submission.R;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.ui.DetailActivity;
import com.ivlie7.submission.util.ApiUtils;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyHolder> {

    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        final Movie movie = movieList.get(i);

        myHolder.textViewTitle.setText(movie.getTitle());
        myHolder.textViewRating.setText(String.valueOf(movie.getVoteAverage()));
        myHolder.textViewRelease.setText(movie.getReleaseDate());
        Glide.with(context).load(ApiUtils.API_POSTER + movie.getPosterPath()).into(myHolder.imageViewPoster);

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

    class MyHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewRating, textViewRelease;
        ImageView imageViewPoster;

        MyHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.tvTitle);
            textViewRating = itemView.findViewById(R.id.tvRating);
            textViewRelease = itemView.findViewById(R.id.tvRelease);
            imageViewPoster = itemView.findViewById(R.id.ivMovie);
        }
    }
}
