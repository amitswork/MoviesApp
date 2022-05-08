package com.example.movieapp.database;

import static com.example.movieapp.main.ui.MovieApp.appContext;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.movieapp.database.converter.MovieDataConverter;
import com.example.movieapp.database.dao.MoviesDao;
import com.example.movieapp.database.entity.MoviesDbEntity;

@Database(entities = {MoviesDbEntity.class}, version = 1, exportSchema = false)
@TypeConverters(value = {MovieDataConverter.class})
public abstract class MovieStoreDatabase extends RoomDatabase {

    public abstract MoviesDao moviesDao();


    private static final String DB_NAME = "movieDatabase.db";

    static MovieStoreDatabase INSTANCE;

    public static MovieStoreDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (MovieStoreDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            appContext,
                            MovieStoreDatabase.class, DB_NAME
                    ).build();
                }
            }
        }
        return INSTANCE;
    }

}
