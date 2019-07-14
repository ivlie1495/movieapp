package com.ivlie7.submission.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ivlie7.submission.R;
import com.ivlie7.submission.adapter.TvShowAdapter;
import com.ivlie7.submission.model.TvShow;
import com.ivlie7.submission.presenter.TvShowPresenter;
import com.ivlie7.submission.view.TvShowView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentTvShow extends Fragment implements TvShowView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.progressBarTv)
    ProgressBar progressBarTv;

    @BindView(R.id.recyclerViewTv)
    RecyclerView recyclerViewTv;

    @BindView(R.id.swipeRefreshTv)
    SwipeRefreshLayout swipeRefreshTv;

    private TvShowPresenter tvShowPresenter;
    private TvShowAdapter tvShowAdapter;

    private List<TvShow> tvShows = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (tvShows != null) {
            outState.putParcelableArrayList(getString(R.string.data), new ArrayList<>(tvShows));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            List<TvShow> outStateTvShowList =savedInstanceState.getParcelableArrayList(getString(R.string.data));
            if (outStateTvShowList != null) {
                tvShowAdapter = new TvShowAdapter(outStateTvShowList, getContext());
                recyclerViewTv.setAdapter(tvShowAdapter);
                tvShows.addAll(outStateTvShowList);
            }
        }

        tvShowPresenter = new TvShowPresenter(this, getString(R.string.set_language));
        tvShowPresenter.getTvShowList(tvShows);

        swipeRefreshTv.setOnRefreshListener(this);
    }

    @Override
    public void showLoading() {
        progressBarTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBarTv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getTvShowList(List<TvShow> tvShowList) {
        if (tvShowList != null) {
            tvShows.clear();
            tvShows.addAll(tvShowList);
            tvShowAdapter = new TvShowAdapter(tvShows, getContext());
            recyclerViewTv.setAdapter(tvShowAdapter);
        }
    }

    @Override
    public void dataNotFound() {
        Toast.makeText(getContext(), getString(R.string.data_not_found), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        swipeRefreshTv.setRefreshing(false);
        tvShowPresenter.getTvShowList(tvShows);
        progressBarTv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        tvShowAdapter = new TvShowAdapter(tvShows, getContext());
        recyclerViewTv.setAdapter(tvShowAdapter);
    }
}
