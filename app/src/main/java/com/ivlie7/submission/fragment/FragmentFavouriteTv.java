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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentFavouriteTv extends Fragment implements TvShowView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private TvShowPresenter favouriteTvShowPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        favouriteTvShowPresenter = new TvShowPresenter(this);
        favouriteTvShowPresenter.getFavouriteTvShowList(getContext());

        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
        favouriteTvShowPresenter.getFavouriteTvShowList(getContext());
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
        favouriteTvShowPresenter.getFavouriteTvShowList(getContext());
    }
}
