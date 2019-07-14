package com.ivlie7.submission.ui;

import android.R.id;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ivlie7.submission.R;
import com.ivlie7.submission.config.RoomConfig;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.model.TvShow;
import com.ivlie7.submission.util.ApiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.progressBarDetail)
    ProgressBar progressBarDetail;

    @BindView(R.id.imageViewBackdropDetail)
    ImageView imageViewBackdropDetail;

    @BindView(R.id.imageViewPosterDetail)
    ImageView imageViewPosterDetail;

    @BindView(R.id.textViewTitleDetail)
    TextView textViewTitleDetail;

    @BindView(R.id.textViewReleaseDetail)
    TextView textViewReleaseDetail;

    @BindView(R.id.textViewRatingDetail)
    TextView textViewRatingDetail;

    @BindView(R.id.textViewOverviewDetail)
    TextView textViewOverviewDetail;

    private Movie movie;
    private TvShow tvShow;

    private Menu menuItem;
    private boolean isFavourite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        showProgressBar();
        getDataFromIntent();
        getDataDetail();
        favouriteState();
    }

    public void getDataDetail() {
        if (movie != null || tvShow != null) {
            textViewTitleDetail.setText(isMovie() ? movie.getTitle() : tvShow.getName());
            textViewReleaseDetail.setText(isMovie() ? movie.getReleaseDate() : tvShow.getFirstAirDate());
            textViewRatingDetail.setText(String.valueOf(isMovie() ? movie.getVoteAverage() : tvShow.getVoteAverage()));
            textViewOverviewDetail.setText(isMovie() ? movie.getOverview() : tvShow.getOverview());
            Glide.with(this)
                    .load(ApiUtils.API_BACKDROP + (isMovie() ? movie.getBackdropPath() : tvShow.getBackdropPath()))
                    .into(imageViewBackdropDetail);
            Glide.with(this)
                    .load(ApiUtils.API_POSTER + (isMovie() ? movie.getPosterPath() : tvShow.getPosterPath()))
                    .into(imageViewPosterDetail);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite, menu);
        menuItem = menu;
        setFavourite();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case id.home:
                this.onBackPressed();
                break;
            case R.id.action_favourite:
                addOrRemoveToFavourite();
                setFavourite();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDataFromIntent() {
        if (isMovie()) {
            movie = getIntent().getParcelableExtra(getString(R.string.data));
        } else {
            tvShow = getIntent().getParcelableExtra(getString(R.string.data));
        }
    }

    public void addOrRemoveToFavourite() {
        if (isFavourite) {
            if (isMovie()) {
                RoomConfig.getInstance(this).movieDao().deleteFromFavourite(movie);
            } else {
                RoomConfig.getInstance(this).tvShowDao().deleteFromFavourite(tvShow);
            }
            Toast.makeText(this, getString(R.string.remove_from_favourite), Toast.LENGTH_SHORT).show();
        } else {
            if (isMovie()) {
                RoomConfig.getInstance(this).movieDao().addToFavourite(movie);
            } else {
                RoomConfig.getInstance(this).tvShowDao().addToFavourite(tvShow);
            }
            Toast.makeText(this, getString(R.string.add_to_favourite), Toast.LENGTH_SHORT).show();
        }

        isFavourite = !isFavourite;
    }

    public void setFavourite() {
        if (isFavourite) {
            menuItem.getItem(0).setIcon(R.drawable.icon_added_to_favourite);
        } else {
            menuItem.getItem(0).setIcon(R.drawable.icon_add_to_favourite);
        }
    }

    public void favouriteState() {
        if (isMovie()) {
            isFavourite = RoomConfig.getInstance(this).movieDao().findMovieById(movie.getId()) != null;
        } else {
            isFavourite = RoomConfig.getInstance(this).tvShowDao().findTvShowById(tvShow.getId()) != null;
        }
    }

    public boolean isMovie() {
        return getIntent().getBooleanExtra("isMovie", true);
    }

    public void showProgressBar() {
        progressBarDetail.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBarDetail.setVisibility(View.INVISIBLE);
            }
        }, 2000);
    }
}
