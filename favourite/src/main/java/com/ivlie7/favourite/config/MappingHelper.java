package com.ivlie7.favourite.config;

import android.database.Cursor;

import com.ivlie7.favourite.model.Movie;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Movie> mapCursorToArrayList(Cursor cursor) {
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
