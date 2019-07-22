package com.ivlie7.submission.presenter;

import android.support.annotation.NonNull;

import com.ivlie7.submission.config.ApiConfig;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingPresenter {

    private String language;

    public SettingPresenter(String language) {
        this.language = language;
    }

    public List<Movie> getUpcomingMovie() {
        final List<Movie> movieList = new ArrayList<>();
        ApiConfig apiConfig = new ApiConfig(language);
        Call<MovieResponse> apiService = apiConfig.getService().getListMovie();
        apiService.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.body() != null) {
                    movieList.addAll(response.body().getGetMovieList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {

            }
        });

        return movieList;
    }
}
