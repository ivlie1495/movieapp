package com.ivlie7.submission.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ivlie7.submission.R;
import com.ivlie7.submission.adapter.TvShowAdapter;
import com.ivlie7.submission.base.BaseFragment;
import com.ivlie7.submission.model.TvShow;
import com.ivlie7.submission.presenter.TvShowPresenter;
import com.ivlie7.submission.ui.SearchActivity;
import com.ivlie7.submission.view.TvShowView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class FragmentTvShow extends BaseFragment implements TvShowView, SwipeRefreshLayout.OnRefreshListener {

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
                recyclerView.setAdapter(tvShowAdapter);
                tvShows = outStateTvShowList;
            }
        }

        tvShowPresenter = new TvShowPresenter(this, getString(R.string.set_language));
        tvShowPresenter.getTvShowList(tvShows);

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
    public void getTvShowList(List<TvShow> tvShowList) {
        if (tvShowList != null) {
            tvShows = tvShowList;
            tvShowAdapter = new TvShowAdapter(tvShows, getContext());
            recyclerView.setAdapter(tvShowAdapter);
        }
    }

    @Override
    public void dataNotFound() {
        Toast.makeText(getContext(), getString(R.string.data_not_found), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
        tvShowPresenter.getTvShowList(tvShows);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_button, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            intent.putExtra("isMovie", false);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
