package com.ivlie7.submission.config;

import com.ivlie7.submission.model.MovieResponse;
import com.ivlie7.submission.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("discover/movie")
    Call<MovieResponse> getListMovie();

    @GET("discover/tv")
    Call<TvShowResponse> getListTv();
}
