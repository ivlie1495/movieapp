package com.ivlie7.submission.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.ivlie7.submission.R;
import com.ivlie7.submission.adapter.MovieAdapter;
import com.ivlie7.submission.adapter.TvShowAdapter;
import com.ivlie7.submission.presenter.MoviePresenter;
import com.ivlie7.submission.presenter.TvShowPresenter;
import com.ivlie7.submission.ui.SettingActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseFragment<T extends Parcelable> extends Fragment {

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;

    @BindView(R.id.swipeRefresh)
    public SwipeRefreshLayout swipeRefresh;

    protected TvShowPresenter tvShowPresenter;
    protected TvShowAdapter tvShowAdapter;

    protected MoviePresenter moviePresenter;
    protected MovieAdapter movieAdapter;

    protected List<T> originalList = new ArrayList<>();
    protected List<T> list = new ArrayList<>();

    protected MenuItem menuItem;
    protected SearchView searchView;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (originalList != null) {
            outState.putParcelableArrayList(getString(R.string.data), new ArrayList<>(originalList));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent intent = new Intent(getContext(), SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setOriginalList(List<T> list) {
        if (originalList.isEmpty() && !list.isEmpty()) {
            originalList = list;
        }
    }
}
