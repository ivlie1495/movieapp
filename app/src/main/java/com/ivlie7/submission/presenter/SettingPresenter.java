package com.ivlie7.submission.presenter;

import com.ivlie7.submission.config.ApiConfig;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.model.MovieResponse;
import com.ivlie7.submission.util.DateUtils;
import com.ivlie7.submission.view.SettingView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingPresenter {

    private SettingView settingView;
    private String language;

    public SettingPresenter(SettingView settingView, String language) {
        this.settingView = settingView;
        this.language = language;
    }

    public void setReminder() {
        ApiConfig apiConfig = new ApiConfig(language);
        Call<MovieResponse> apiService = apiConfig.getService().getListMovie();
        apiService.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.body() != null) {
                    List<Movie> movieList = response.body().getGetMovieList();
                    for (Movie movie : movieList) {
                        if (movie.getReleaseDate().equals(DateUtils.getCurrentDate())) {
                            settingView.setReminder(movie);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    public void cancelReminder() {
        settingView.cancelReminder();
    }
}
