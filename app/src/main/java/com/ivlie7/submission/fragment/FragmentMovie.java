package com.ivlie7.submission.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.ivlie7.submission.R;
import com.ivlie7.submission.adapter.MovieAdapter;
import com.ivlie7.submission.base.BaseFragment;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.presenter.MoviePresenter;
import com.ivlie7.submission.view.MovieView;

import java.util.ArrayList;
import java.util.List;

public class FragmentMovie extends BaseFragment<Movie> implements MovieView, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (originalList != null) {
            outState.putParcelableArrayList(getString(R.string.data), new ArrayList<>(originalList));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            List<Movie> outStateMovieList =savedInstanceState.getParcelableArrayList(getString(R.string.data));
            if (outStateMovieList != null) {
                movieAdapter = new MovieAdapter(outStateMovieList, getContext());
                recyclerView.setAdapter(movieAdapter);
                list = outStateMovieList;
            }
        }

        moviePresenter = new MoviePresenter(this, getString(R.string.set_language));
        moviePresenter.getMovieList(list);

        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void showLoading() {
        recyclerView.setAdapter(null);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getMovieList(List<Movie> movieList) {
        if (movieList != null) {
            list = movieList;
            setOriginalList(list);
            movieAdapter = new MovieAdapter(list, getContext());
            recyclerView.setAdapter(movieAdapter);
        }
    }

    @Override
    public void dataNotFound() {
        Toast.makeText(getContext(), getString(R.string.data_not_found), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
        moviePresenter.getMovieList(list);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action_menu, menu);

        menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.search));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.isEmpty()) {
            moviePresenter.searchMovie(newText, originalList);
        } else {
            moviePresenter.getMovieList(originalList);
        }
        return true;
    }
}
