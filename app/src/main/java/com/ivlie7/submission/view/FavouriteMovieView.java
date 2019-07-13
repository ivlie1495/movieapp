package com.ivlie7.submission.view;

import com.ivlie7.submission.model.Movie;

import java.util.List;

public interface FavouriteMovieView {
    void showLoading();
    void hideLoading();
    void getMovieList(List<Movie> movieList);
    void dataNotFound();
}
