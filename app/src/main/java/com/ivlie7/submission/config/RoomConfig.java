package com.ivlie7.submission.config;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ivlie7.submission.dao.MovieDao;
import com.ivlie7.submission.dao.TvShowDao;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.model.TvShow;

@Database(entities = {Movie.class, TvShow.class}, version = 2)
public abstract class RoomConfig extends RoomDatabase {

    public abstract MovieDao movieDao();
    public abstract TvShowDao tvShowDao();
    private static RoomConfig mInstance;

    public static RoomConfig getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, RoomConfig.class, "db.Favourite")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return mInstance;
    }
}
