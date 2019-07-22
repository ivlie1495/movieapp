package com.ivlie7.favourite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivlie7.favourite.R;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewTitle, textViewRating, textViewRelease;
    public ImageView imageViewPoster;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewTitle = itemView.findViewById(R.id.tvTitle);
        textViewRating = itemView.findViewById(R.id.tvRating);
        textViewRelease = itemView.findViewById(R.id.tvRelease);
        imageViewPoster = itemView.findViewById(R.id.ivMovie);
    }
}
