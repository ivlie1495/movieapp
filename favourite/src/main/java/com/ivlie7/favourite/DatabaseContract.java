package com.ivlie7.favourite;

import android.net.Uri;

class DatabaseContract {
    private static final String TABLE_MOVIES = "Movie";
    private static final String AUTHORITY = "com.ivlie7.submission.config.DatabaseProvider";

    static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIES)
            .build();
}
