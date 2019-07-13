package com.ivlie7.submission.view;

import com.ivlie7.submission.model.TvShow;

import java.util.List;

public interface TvShowView {
    void showLoading();
    void hideLoading();
    void getTvShowList(List<TvShow> tvShowList);
    void dataNotFound();
}
