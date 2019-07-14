package com.ivlie7.submission.presenter;

import android.content.Context;

import com.ivlie7.submission.config.ApiConfig;
import com.ivlie7.submission.config.RoomConfig;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.model.MovieResponse;
import com.ivlie7.submission.view.MovieView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePresenter {

    private MovieView movieView;
    private String language;

    public MoviePresenter(MovieView movieView) {
        this.movieView = movieView;
    }

    public MoviePresenter(MovieView movieView, String language) {
        this.movieView = movieView;
        this.language = language;
    }

    public void getMovieList(final List<Movie> movies) {
        ApiConfig apiConfig = new ApiConfig(language);
        Call<MovieResponse> apiService = apiConfig.getService().getListMovie();

        movieView.showLoading();
        apiService.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieView.hideLoading();
                if (response.body() != null) {
                    List<Movie> movieList = response.body().getGetMovieList();
                    movieView.getMovieList(movieList);
                } else {
                    movieView.dataNotFound();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                movieView.hideLoading();
                if (movies != null) {
                    movieView.getMovieList(movies);
                }
            }
        });
    }

    public void getFavouriteMovieList(Context context) {
        movieView.showLoading();
        List<Movie> favouriteMovieList = RoomConfig.getInstance(context.getApplicationContext()).movieDao().getFavouriteMovie();
        if (favouriteMovieList != null) {
            movieView.getMovieList(favouriteMovieList);
            movieView.hideLoading();
        } else {
            movieView.dataNotFound();
        }
    }
}
