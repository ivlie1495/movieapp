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
import com.ivlie7.submission.model.TvShow;
import com.ivlie7.submission.util.ApiUtils;
import com.ivlie7.submission.ui.DetailActivity;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.MyHolder> {

    private List<TvShow> tvShowList;
    private Context context;

    public TvShowAdapter(List<TvShow> tvShowList, Context context) {
        this.tvShowList = tvShowList;
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
        final TvShow tvShow = tvShowList.get(i);

        myHolder.textViewTitle.setText(tvShow.getName());
        myHolder.textViewRating.setText(String.valueOf(tvShow.getVoteAverage()));
        myHolder.textViewRelease.setText(tvShow.getFirstAirDate());
        Glide.with(context).load(ApiUtils.API_POSTER + tvShow.getPosterPath()).into(myHolder.imageViewPoster);

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(context.getString(R.string.data), tvShow);
                intent.putExtra("isMovie", false);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
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

//    private StringBuilder getGenre(List<Integer> genreIds, List<Genre> getGenreList) {
//        StringBuilder movieGenre = new StringBuilder();
//        if (getGenreList != null) {
//            for (Genre genre : getGenreList) {
//                if (genreIds.contains(genre.getId())) {
//                    movieGenre.append(" ").append(genre.getName()).append(",");
//                }
//            }
//            movieGenre = movieGenre.deleteCharAt(0);
//            movieGenre = movieGenre.deleteCharAt(movieGenre.length() - 1);
//        }
//        return movieGenre;
//    }
}
