package com.ivlie7.favourite.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ivlie7.favourite.R;
import com.ivlie7.favourite.adapter.MovieAdapter;
import com.ivlie7.favourite.config.DatabaseProvider;
import com.ivlie7.favourite.presenter.MoviePresenter;
import com.ivlie7.favourite.view.MovieView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    MoviePresenter moviePresenter = new MoviePresenter(this);
    MovieAdapter movieAdapter;
    private static final int MOVIE_LOADER = 1;

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
            if (i == MOVIE_LOADER) {
                return new CursorLoader(getApplicationContext(),
                        DatabaseProvider.URI_MOVIE,
                        new String[]{"id",
                                "name",
                                "poster_path",
                                "backdrop_path",
                                "overview",
                                "release_date",
                                "vote_average"},
                        null,
                        null,
                        null);
            }
            throw new IllegalArgumentException();
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
            if (loader.getId() == MOVIE_LOADER) {
                movieAdapter.setMovie(cursor);
                moviePresenter.getFavouriteMovieList(getApplicationContext(), cursor);
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            if (loader.getId() == MOVIE_LOADER) {
                movieAdapter.setMovie(null);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        swipeRefresh.setOnRefreshListener(this);

        getSupportLoaderManager().initLoader(MOVIE_LOADER, null, loaderCallbacks);

        movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
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
    public void dataNotFound() {
        Toast.makeText(this, getString(R.string.data_not_found), Toast.LENGTH_SHORT).show();
    }
}
