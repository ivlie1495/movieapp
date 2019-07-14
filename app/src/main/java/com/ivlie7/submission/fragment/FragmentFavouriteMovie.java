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
import com.ivlie7.submission.adapter.MovieAdapter;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.presenter.MoviePresenter;
import com.ivlie7.submission.view.MovieView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentFavouriteMovie extends Fragment implements MovieView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerViewFavouriteMovie)
    RecyclerView recyclerViewFavouriteMovie;

    @BindView(R.id.progressBarFavouriteMovie)
    ProgressBar progressBarFavouriteMovie;

    @BindView(R.id.swipeRefreshFavouriteMovie)
    SwipeRefreshLayout swipeRefreshFavouriteMovie;

    MoviePresenter favouriteMoviePresenter;
    MovieAdapter movieAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        favouriteMoviePresenter = new MoviePresenter(this);
        favouriteMoviePresenter.getFavouriteMovieList(getContext());

        swipeRefreshFavouriteMovie.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        swipeRefreshFavouriteMovie.setRefreshing(false);
        favouriteMoviePresenter.getFavouriteMovieList(getContext());
        progressBarFavouriteMovie.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoading() {
        progressBarFavouriteMovie.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBarFavouriteMovie.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getMovieList(List<Movie> movieList) {
        movieAdapter = new MovieAdapter(movieList, getContext());
        recyclerViewFavouriteMovie.setAdapter(movieAdapter);
    }

    @Override
    public void dataNotFound() {
        Toast.makeText(getContext(), R.string.data_not_found, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        favouriteMoviePresenter.getFavouriteMovieList(getContext());
    }
}
