package com.example.movieapp.main.di;

import com.example.movieapp.common.network.ApiService;
import com.example.movieapp.common.network.RetrofitClient;
import com.example.movieapp.database.MovieStoreDatabase;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainActivityModule {

    @Provides
    MovieStoreDatabase providesMovieDatabase() {
        return MovieStoreDatabase.getInstance();
    }

    @Provides
    Retrofit providesRetrofit() {
        return RetrofitClient.getInstance();
    }

    @Provides
    ApiService providesApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

}
