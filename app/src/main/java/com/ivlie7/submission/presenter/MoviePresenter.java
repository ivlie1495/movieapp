package com.ivlie7.submission.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.ivlie7.submission.config.ApiConfig;
import com.ivlie7.submission.config.RoomConfig;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.model.MovieResponse;
import com.ivlie7.submission.view.MovieView;

import java.util.ArrayList;
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
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                movieView.hideLoading();
                if (response.body() != null) {
                    List<Movie> movieList = response.body().getGetMovieList();
                    movieView.getMovieList(movieList);
                } else {
                    movieView.dataNotFound();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
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

    public void searchMovie(final String query, final List<Movie> oriListMovie) {
        ApiConfig apiConfig = new ApiConfig(language);
        Call<MovieResponse> apiService = apiConfig.getService().getListSearchMovie(query);

        movieView.showLoading();
        apiService.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.body() != null) {
                    List<Movie> movieList = response.body().getGetMovieList();
                    movieView.getMovieList(movieList);
                    movieView.hideLoading();
                } else {
                    movieView.dataNotFound();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                movieView.hideLoading();
                if (oriListMovie != null) {
                    movieView.getMovieList(filterList(query, oriListMovie));
                }
            }
        });
    }

    private List<Movie> filterList(String query, List<Movie> movieList) {
        List<Movie> filteredListMovie = new ArrayList<>();

        if (!query.isEmpty()) {
            for (Movie movie : movieList) {
                if (movie.getTitle().toLowerCase().contains(query)) {
                    filteredListMovie.add(movie);
                }
            }
        }
        return filteredListMovie;
    }
}
