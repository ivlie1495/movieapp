package com.ivlie7.submission.view;

import android.content.Context;

import com.ivlie7.submission.model.Movie;

import java.util.List;

public interface SettingView {
    void getUpcomingMovieList(Context context, List<Movie> movies);
}
