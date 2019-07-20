package com.ivlie7.submission.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ivlie7.submission.config.ApiConfig;
import com.ivlie7.submission.config.RoomConfig;
import com.ivlie7.submission.model.TvShow;
import com.ivlie7.submission.model.TvShowResponse;
import com.ivlie7.submission.view.TvShowView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowPresenter {

    private TvShowView tvShowView;
    private String language;

    public TvShowPresenter(TvShowView tvShowView) {
        this.tvShowView = tvShowView;
    }

    public TvShowPresenter(TvShowView tvShowView, String language) {
        this.tvShowView = tvShowView;
        this.language = language;
    }

    public void getTvShowList(final List<TvShow> tvShows) {
        ApiConfig apiConfig = new ApiConfig(language);
        Call<TvShowResponse> apiService = apiConfig.getService().getListTv();

        tvShowView.showLoading();
        apiService.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                tvShowView.hideLoading();
                if (response.body() != null) {
                    List<TvShow> tvShowList = response.body().getTvShowList();
                    tvShowView.getTvShowList(tvShowList);
                } else {
                    tvShowView.dataNotFound();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                tvShowView.hideLoading();
                if (tvShows != null) {
                    tvShowView.getTvShowList(tvShows);
                }
            }
        });
    }

    public void getFavouriteTvShowList(Context context) {
        tvShowView.showLoading();
        List<TvShow> favouriteTvList = RoomConfig.getInstance(context.getApplicationContext()).tvShowDao().getFavouriteTvShow();
        if (favouriteTvList != null) {
            tvShowView.getTvShowList(favouriteTvList);
            tvShowView.hideLoading();
        } else {
            tvShowView.dataNotFound();
        }
    }

    public void searchTvShow(final String query, final List<TvShow> oriListTvShow) {
        ApiConfig apiConfig = new ApiConfig(language);
        Call<TvShowResponse> apiService = apiConfig.getService().getListSearchTv(query);

        tvShowView.showLoading();
        apiService.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                if (response.body() != null) {
                    List<TvShow> tvShowList = response.body().getTvShowList();
                    tvShowView.getTvShowList(tvShowList);
                    tvShowView.hideLoading();
                } else {
                    tvShowView.dataNotFound();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                tvShowView.hideLoading();
                if (oriListTvShow != null) {
                    tvShowView.getTvShowList(filterList(query, oriListTvShow));
                }
            }
        });
    }

    private List<TvShow> filterList(String query, List<TvShow> tvShowList) {
        List<TvShow> filteredListMovie = new ArrayList<>();

        if (!query.isEmpty()) {
            for (TvShow tvShow : tvShowList) {
                if (tvShow.getName().toLowerCase().contains(query)) {
                    filteredListMovie.add(tvShow);
                }
            }
        }
        return filteredListMovie;
    }
}
