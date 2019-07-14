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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentMovie extends Fragment implements MovieView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerViewMovie)
    RecyclerView recyclerViewMovie;

    @BindView(R.id.progressBarMovie)
    ProgressBar progressBarMovie;

    @BindView(R.id.swipeRefreshMovie)
    SwipeRefreshLayout swipeRefreshMovie;

    private MoviePresenter moviePresenter;
    private MovieAdapter movieAdapter;

    private final List<Movie> movies = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movies != null) {
            outState.putParcelableArrayList(getString(R.string.data), new ArrayList<>(movies));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(FragmentMovie.this, view);

        if (savedInstanceState != null) {
            List<Movie> outStateMovieList =savedInstanceState.getParcelableArrayList(getString(R.string.data));
            if (outStateMovieList != null) {
                movieAdapter = new MovieAdapter(outStateMovieList, getContext());
                recyclerViewMovie.setAdapter(movieAdapter);
                movies.addAll(outStateMovieList);
            }
        }

        moviePresenter = new MoviePresenter(this, getString(R.string.set_language));
        moviePresenter.getMovieList(movies);

        swipeRefreshMovie.setOnRefreshListener(this);
    }

    @Override
    public void showLoading() {
        progressBarMovie.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBarMovie.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getMovieList(List<Movie> movieList) {
        if (movieList != null) {
            movies.clear();
            movies.addAll(movieList);
            movieAdapter = new MovieAdapter(movies, getContext());
            recyclerViewMovie.setAdapter(movieAdapter);
        }
    }

    @Override
    public void dataNotFound() {
        Toast.makeText(getContext(), getString(R.string.data_not_found), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        swipeRefreshMovie.setRefreshing(false);
        moviePresenter.getMovieList(movies);
        progressBarMovie.setVisibility(View.INVISIBLE);
    }
}
