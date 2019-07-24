package com.ivlie7.favourite.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
