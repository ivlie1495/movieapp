package com.ivlie7.submission.view;

import com.ivlie7.submission.model.TvShow;

import java.util.List;

public interface FavouriteTvView {
    void showLoading();
    void hideLoading();
    void getTvShowList(List<TvShow> tvShowList);
    void dataNotFound();
}
