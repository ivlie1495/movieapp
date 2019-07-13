package com.ivlie7.submission.presenter;

import android.content.Context;

import com.ivlie7.submission.config.RoomConfig;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.view.FavouriteMovieView;

import java.util.List;

public class FavouriteMoviePresenter {

    private FavouriteMovieView favouriteMovieView;

    public FavouriteMoviePresenter(FavouriteMovieView favouriteMovieView) {
        this.favouriteMovieView = favouriteMovieView;
    }

    public void getFavouriteMovieList(Context context) {
        favouriteMovieView.showLoading();
        List<Movie> favouriteMovieList = RoomConfig.getInstance(context.getApplicationContext()).movieDao().getFavouriteMovie();
        if (favouriteMovieList != null) {
            favouriteMovieView.getMovieList(favouriteMovieList);
            favouriteMovieView.hideLoading();
        } else {
            favouriteMovieView.dataNotFound();
        }
    }
}
