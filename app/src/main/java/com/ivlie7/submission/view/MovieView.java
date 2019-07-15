package com.ivlie7.submission.view;

import com.ivlie7.submission.base.BaseView;
import com.ivlie7.submission.model.Movie;

import java.util.List;

public interface MovieView extends BaseView {
    void getMovieList(List<Movie> movieList);
}
