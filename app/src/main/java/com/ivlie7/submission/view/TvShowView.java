package com.ivlie7.submission.view;

import com.ivlie7.submission.base.BaseView;
import com.ivlie7.submission.model.TvShow;

import java.util.List;

public interface TvShowView extends BaseView {
    void getTvShowList(List<TvShow> tvShowList);
}
