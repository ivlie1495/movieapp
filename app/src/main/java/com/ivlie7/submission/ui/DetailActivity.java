package com.ivlie7.submission.ui;

import android.R.id;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ivlie7.submission.R;
import com.ivlie7.submission.config.RoomConfig;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.model.TvShow;
import com.ivlie7.submission.constant.ApiConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

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

        getDataFromIntent();
        getDataDetail();
        favouriteState();
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

    private void getDataDetail() {
        if (movie != null || tvShow != null) {
            textViewTitleDetail.setText(isMovie() ? movie.getTitle() : tvShow.getName());
            textViewReleaseDetail.setText(isMovie() ? movie.getReleaseDate() : tvShow.getFirstAirDate());
            textViewRatingDetail.setText(String.valueOf(isMovie() ? movie.getVoteAverage() : tvShow.getVoteAverage()));
            textViewOverviewDetail.setText(isMovie() ? movie.getOverview() : tvShow.getOverview());
            Glide.with(this)
                    .load(ApiConstants.API_BACKDROP + (isMovie() ? movie.getBackdropPath() : tvShow.getBackdropPath()))
                    .into(imageViewBackdropDetail);
            Glide.with(this)
                    .load(ApiConstants.API_POSTER + (isMovie() ? movie.getPosterPath() : tvShow.getPosterPath()))
                    .into(imageViewPosterDetail);
        }
    }

    private void getDataFromIntent() {
        if (isMovie()) {
            movie = getIntent().getParcelableExtra(getString(R.string.data));
        } else {
            tvShow = getIntent().getParcelableExtra(getString(R.string.data));
        }
    }

    private void addOrRemoveToFavourite() {
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

    private void setFavourite() {
        if (isFavourite) {
            menuItem.getItem(0).setIcon(R.drawable.icon_added_to_favourite);
        } else {
            menuItem.getItem(0).setIcon(R.drawable.icon_add_to_favourite);
        }
    }

    private void favouriteState() {
        if (isMovie()) {
            isFavourite = RoomConfig.getInstance(this).movieDao().findMovieById(movie.getId()) != null;
        } else {
            isFavourite = RoomConfig.getInstance(this).tvShowDao().findTvShowById(tvShow.getId()) != null;
        }
    }

    private boolean isMovie() {
        return getIntent().getBooleanExtra("isMovie", true);
    }
}
