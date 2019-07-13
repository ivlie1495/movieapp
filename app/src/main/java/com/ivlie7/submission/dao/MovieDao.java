package com.ivlie7.submission.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ivlie7.submission.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movie")
    List<Movie> getFavouriteMovie();

    @Query("SELECT * FROM Movie WHERE id = :id")
    Movie findMovieById(int id);

    @Insert
    void addToFavourite(Movie movie);

    @Delete
    void deleteFromFavourite(Movie movie);
}
