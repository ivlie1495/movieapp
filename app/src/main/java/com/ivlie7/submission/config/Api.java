package com.ivlie7.submission.config;

import com.ivlie7.submission.model.MovieResponse;
import com.ivlie7.submission.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("movie/upcoming")
    Call<MovieResponse> getListMovie();

    @GET("discover/tv")
    Call<TvShowResponse> getListTv();

    @GET("search/movie")
    Call<MovieResponse> getListSearchMovie(@Query("query") String query);

    @GET("search/tv")
    Call<TvShowResponse> getListSearchTv(@Query("query") String query);
}
