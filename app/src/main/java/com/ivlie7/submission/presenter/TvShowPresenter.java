package com.ivlie7.submission.presenter;

import com.ivlie7.submission.config.ApiConfig;
import com.ivlie7.submission.model.TvShow;
import com.ivlie7.submission.model.TvShowResponse;
import com.ivlie7.submission.view.TvShowView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowPresenter {

    private TvShowView tvShowView;
    private String language;

    public TvShowPresenter(TvShowView tvShowView, String language) {
        this.tvShowView = tvShowView;
        this.language = language;
    }

    public void getTvShowList() {
        ApiConfig apiConfig = new ApiConfig(language);
        Call<TvShowResponse> apiService = apiConfig.getService().getListTv();

        tvShowView.showLoading();
        apiService.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                tvShowView.hideLoading();
                if (response.body() != null) {
                    List<TvShow> tvShowList = response.body().getTvShowList();
                    tvShowView.getTvShowList(tvShowList);
                } else {
                    tvShowView.dataNotFound();
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                tvShowView.hideLoading();
            }
        });
    }
}
