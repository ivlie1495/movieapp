package com.ivlie7.submission.config;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.ivlie7.submission.dao.MovieDao;
import com.ivlie7.submission.model.Movie;

public class DatabaseProvider extends ContentProvider {

    private static final String AUTHORITY = "com.ivlie7.submission";
    public static final Uri URI_MOVIE = Uri.parse("content://" + AUTHORITY + "/Movie");

    private static final int CODE_MOVIE_DIR = 1;
    private static final int CODE_MOVIE_ITEM = 2;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, "Movie", CODE_MOVIE_DIR);
        URI_MATCHER.addURI(AUTHORITY, "Movie" + "/*", CODE_MOVIE_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@Nullable Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int match = URI_MATCHER.match(uri);
        if (match == CODE_MOVIE_DIR || match == CODE_MOVIE_ITEM) {
            Context context = getContext();
            if (context == null) {
                return null;
            }

            MovieDao movieDao = RoomConfig.getInstance(context).movieDao();
            Cursor cursor = null;
            if (match == CODE_MOVIE_DIR && uri != null) {
                cursor = movieDao.getAllFavouriteMovie();
                cursor.setNotificationUri(context.getContentResolver(), uri);
            }

            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@Nullable Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + "Movie";
            case CODE_MOVIE_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + "Movie";
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@Nullable Uri uri, @Nullable ContentValues values) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                Context context = getContext();
                long id = 0;
                if (context == null) {
                    return null;
                }

                if (uri != null && values != null) {
                    context.getContentResolver().notifyChange(uri, null);
                    id = RoomConfig.getInstance(context).movieDao().addToFavourite(Movie.fromContentValues(values));
                }

                return ContentUris.withAppendedId(uri, id);
            case CODE_MOVIE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@Nullable Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_MOVIE_ITEM:
                Context context = getContext();
                int count = 0;
                if (context == null) {
                    return 0;
                }

                if (uri != null) {
                    count = RoomConfig.getInstance(context).movieDao().deleteById(ContentUris.parseId(uri));
                    context.getContentResolver().notifyChange(uri, null);
                }
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@Nullable Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_MOVIE_ITEM:
                Context context = getContext();
                int count = 0;

                if (context == null) {
                    return 0;
                }

                if (values != null && uri != null) {
                    Movie movie = Movie.fromContentValues(values);
                    movie.setId((int) ContentUris.parseId(uri));

                    context.getContentResolver().notifyChange(uri, null);
                    count = RoomConfig.getInstance(context).movieDao().update(movie);
                    context.getContentResolver().notifyChange(uri, null);
                }
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
