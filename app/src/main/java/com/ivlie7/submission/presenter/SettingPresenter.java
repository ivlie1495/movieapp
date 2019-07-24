package com.ivlie7.submission.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.ivlie7.submission.config.ApiConfig;
import com.ivlie7.submission.model.MovieResponse;
import com.ivlie7.submission.view.SettingView;

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

    public void getUpcomingMovie(final Context context) {
        ApiConfig apiConfig = new ApiConfig(language);
        Call<MovieResponse> apiService = apiConfig.getService().getListMovie();
        apiService.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.body() != null) {
                    settingView.getUpcomingMovieList(context, response.body().getGetMovieList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {

            }
        });
    }
}
