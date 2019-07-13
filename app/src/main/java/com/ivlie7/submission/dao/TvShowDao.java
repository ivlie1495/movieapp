package com.ivlie7.submission.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ivlie7.submission.model.TvShow;

import java.util.List;

@Dao
public interface TvShowDao {

    @Query("SELECT * FROM TvShow")
    List<TvShow> getFavouriteTvShow();

    @Query("SELECT * FROM TvShow WHERE id = :id")
    TvShow findTvShowById(int id);

    @Insert
    void addToFavourite(TvShow tvShow);

    @Delete
    void deleteFromFavourite(TvShow tvShow);
}
