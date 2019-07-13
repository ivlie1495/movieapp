package com.ivlie7.submission.presenter;

import android.content.Context;

import com.ivlie7.submission.config.RoomConfig;
import com.ivlie7.submission.model.TvShow;
import com.ivlie7.submission.view.FavouriteTvView;

import java.util.List;

public class FavouriteTvShowPresenter {

    private FavouriteTvView favouriteTvView;

    public FavouriteTvShowPresenter(FavouriteTvView favouriteTvView) {
        this.favouriteTvView = favouriteTvView;
    }

    public void getFavouriteTvShowList(Context context) {
        favouriteTvView.showLoading();
        List<TvShow> favouriteTvList = RoomConfig.getInstance(context.getApplicationContext()).tvShowDao().getFavouriteTvShow();
        if (favouriteTvList != null) {
            favouriteTvView.getTvShowList(favouriteTvList);
            favouriteTvView.hideLoading();
        } else {
            favouriteTvView.dataNotFound();
        }
    }
}
