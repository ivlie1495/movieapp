package com.ivlie7.submission.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ivlie7.submission.R;
import com.ivlie7.submission.adapter.TvShowAdapter;
import com.ivlie7.submission.base.BaseFragment;
import com.ivlie7.submission.model.TvShow;
import com.ivlie7.submission.presenter.TvShowPresenter;
import com.ivlie7.submission.view.TvShowView;

import java.util.List;

public class FragmentFavouriteTv extends BaseFragment implements TvShowView, SwipeRefreshLayout.OnRefreshListener {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvShowPresenter = new TvShowPresenter(this);
        tvShowPresenter.getFavouriteTvShowList(getContext());

        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
        tvShowPresenter.getFavouriteTvShowList(getContext());
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getTvShowList(List<TvShow> tvShowList) {
        TvShowAdapter tvShowAdapter = new TvShowAdapter(tvShowList, getContext());
        recyclerView.setAdapter(tvShowAdapter);
    }

    @Override
    public void dataNotFound() {
        Toast.makeText(getContext(), R.string.data_not_found, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        tvShowPresenter.getFavouriteTvShowList(getContext());
    }
}
