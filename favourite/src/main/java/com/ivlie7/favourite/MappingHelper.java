package com.ivlie7.favourite;

import android.database.Cursor;

import java.util.ArrayList;

class MappingHelper {

    static ArrayList<Movie> mapCursorToArrayList(Cursor cursor) {
        ArrayList<Movie> movies = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String posterPath = cursor.getString(cursor.getColumnIndexOrThrow("poster_path"));
            String backdropPath = cursor.getString(cursor.getColumnIndexOrThrow("backdrop_path"));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow("overview"));
            String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow("release_date"));
            float voteAverage = cursor.getFloat(cursor.getColumnIndexOrThrow("vote_average"));
            movies.add(new Movie(id, title, posterPath, backdropPath, overview, releaseDate, voteAverage));
        }

        return movies;
    }
}
