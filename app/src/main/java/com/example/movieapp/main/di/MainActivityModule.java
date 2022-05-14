package com.example.movieapp.main.di;

import com.example.movieapp.database.MovieStoreDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    MovieStoreDatabase providesMovieDatabase() {
        return MovieStoreDatabase.getInstance();
    }

}
