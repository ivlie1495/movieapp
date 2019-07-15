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
import com.ivlie7.submission.adapter.MovieAdapter;
import com.ivlie7.submission.base.BaseFragment;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.presenter.MoviePresenter;
import com.ivlie7.submission.view.MovieView;

import java.util.List;

public class FragmentFavouriteMovie extends BaseFragment implements MovieView, SwipeRefreshLayout.OnRefreshListener {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moviePresenter = new MoviePresenter(this);
        moviePresenter.getFavouriteMovieList(getContext());

        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
        moviePresenter.getFavouriteMovieList(getContext());
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
    public void getMovieList(List<Movie> movieList) {
        MovieAdapter movieAdapter = new MovieAdapter(movieList, getContext());
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void dataNotFound() {
        Toast.makeText(getContext(), R.string.data_not_found, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        moviePresenter.getFavouriteMovieList(getContext());
    }
}
