package com.ivlie7.favourite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.ivlie7.favourite.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movie")
    List<Movie> getFavouriteMovie();

    @Query("SELECT * FROM Movie")
    Cursor getAllFavouriteMovie();

    @Insert
    long addToFavourite(Movie movie);

    @Query("DELETE FROM Movie WHERE id = :id")
    int deleteById(long id);

    @Update
    int update(Movie movie);
}
