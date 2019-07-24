package com.ivlie7.submission.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ivlie7.submission.R;
import com.ivlie7.submission.base.BaseViewHolder;
import com.ivlie7.submission.model.TvShow;
import com.ivlie7.submission.constant.ApiConstants;
import com.ivlie7.submission.ui.DetailActivity;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<TvShow> tvShowList;
    private Context context;

    public TvShowAdapter(List<TvShow> tvShowList, Context context) {
        this.tvShowList = tvShowList;
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
        final TvShow tvShow = tvShowList.get(i);

        baseViewHolder.textViewTitle.setText(tvShow.getName());
        baseViewHolder.textViewRating.setText(String.valueOf(tvShow.getVoteAverage()));
        baseViewHolder.textViewRelease.setText(tvShow.getFirstAirDate());
        Glide.with(context).load(ApiConstants.API_POSTER + tvShow.getPosterPath()).into(baseViewHolder.imageViewPoster);

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
}
