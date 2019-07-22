package com.ivlie7.favourite.presenter;

import android.content.Context;
import android.database.Cursor;

import com.ivlie7.favourite.config.RoomConfig;
import com.ivlie7.favourite.model.Movie;
import com.ivlie7.favourite.view.MovieView;

import java.util.List;

public class MoviePresenter {

    private MovieView movieView;
    private Cursor mCursor;

    public MoviePresenter(MovieView movieView) {
        this.movieView = movieView;
    }

    public void getFavouriteMovieList(Context context, Cursor cursor) {
        movieView.showLoading();
        if (cursor != null) {
            mCursor = cursor;
            Movie movie = getItem(1);
        }
        List<Movie> favouriteMovieList = RoomConfig.getInstance(context.getApplicationContext()).movieDao().getFavouriteMovie();
        if (favouriteMovieList != null) {
//            movieView.getMovieList(favouriteMovieList);
            movieView.hideLoading();
        } else {
            movieView.dataNotFound();
        }
    }

    private Movie getItem(int i) {
        if (!mCursor.moveToPosition(i)) {
            throw new IllegalStateException("No Position Found!");
        }

        return new Movie(mCursor);
    }
}
