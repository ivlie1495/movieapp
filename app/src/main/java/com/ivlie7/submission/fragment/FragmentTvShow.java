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
import com.ivlie7.submission.adapter.TvShowAdapter;
import com.ivlie7.submission.base.BaseFragment;
import com.ivlie7.submission.model.TvShow;
import com.ivlie7.submission.presenter.TvShowPresenter;
import com.ivlie7.submission.view.TvShowView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class FragmentTvShow extends BaseFragment<TvShow> implements TvShowView, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setSavedInstanceState(savedInstanceState);

        tvShowPresenter = new TvShowPresenter(this, getString(R.string.set_language));
        tvShowPresenter.getTvShowList(list);

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
            list = tvShowList;
            setOriginalList(list);
            tvShowAdapter = new TvShowAdapter(list, getContext());
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
        tvShowPresenter.getTvShowList(list);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action_menu, menu);
        setSearchView(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.isEmpty()) {
            tvShowPresenter.searchTvShow(newText, originalList);
        } else {
            tvShowPresenter.getTvShowList(originalList);
        }
        return true;
    }

    public void setSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            List<TvShow> outStateTvShowList =savedInstanceState.getParcelableArrayList(getString(R.string.data));
            if (outStateTvShowList != null) {
                tvShowAdapter = new TvShowAdapter(outStateTvShowList, getContext());
                recyclerView.setAdapter(tvShowAdapter);
                list = outStateTvShowList;
            }
        }
    }

    public void setSearchView(Menu menu) {
        menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.search));
    }
}
