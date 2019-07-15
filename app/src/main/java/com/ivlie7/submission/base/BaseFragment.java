package com.ivlie7.submission.base;

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

import com.ivlie7.submission.R;
import com.ivlie7.submission.adapter.MovieAdapter;
import com.ivlie7.submission.adapter.TvShowAdapter;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.model.TvShow;
import com.ivlie7.submission.presenter.MoviePresenter;
import com.ivlie7.submission.presenter.TvShowPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseFragment extends Fragment {

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;

    @BindView(R.id.swipeRefresh)
    public SwipeRefreshLayout swipeRefresh;

    public TvShowPresenter tvShowPresenter;
    public TvShowAdapter tvShowAdapter;

    public MoviePresenter moviePresenter;
    public MovieAdapter movieAdapter;

    public List<Movie> movies = new ArrayList<>();
    public List<TvShow> tvShows = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
