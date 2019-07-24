package com.ivlie7.submission.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ivlie7.submission.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movie")
    List<Movie> getFavouriteMovie();

    @Query("SELECT * FROM Movie WHERE id = :id")
    Movie findMovieById(int id);

    @Insert
    long addToFavourite(Movie movie);

    @Delete
    void deleteFromFavourite(Movie movie);

    @Query("SELECT * FROM Movie")
    Cursor getAllFavouriteMovie();

    @Query("DELETE FROM Movie WHERE id = :id")
    int deleteById(long id);

    @Update
    int update(Movie movie);
}
