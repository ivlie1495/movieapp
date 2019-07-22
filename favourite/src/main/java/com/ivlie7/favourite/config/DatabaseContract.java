package com.ivlie7.favourite.config;

import android.net.Uri;

public class DatabaseContract {
    private static final String TABLE_MOVIES = "Movie";
    private static final String AUTHORITY = "com.ivlie7.submission";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIES)
            .build();
}
