package com.ivlie7.favourite.config;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.ivlie7.favourite.dao.MovieDao;
import com.ivlie7.favourite.model.Movie;

@Database(entities = {Movie.class}, version = 2)
public abstract class RoomConfig extends RoomDatabase {

    public abstract MovieDao movieDao();
    private static RoomConfig mInstance;

    public static RoomConfig getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, RoomConfig.class, "db.Favourite")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return mInstance;
    }

    @VisibleForTesting
    public static void switchToInMemory(Context context) {
        mInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), RoomConfig.class).build();
    }
}
