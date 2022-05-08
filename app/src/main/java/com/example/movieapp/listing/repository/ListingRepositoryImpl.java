package com.example.movieapp.listing.repository;

import androidx.lifecycle.LiveData;

import com.example.movieapp.BuildConfig;
import com.example.movieapp.common.network.ApiService;
import com.example.movieapp.database.MovieStoreDatabase;
import com.example.movieapp.database.entity.MoviesDbEntity;
import com.example.movieapp.listing.helper.SectionName;
import com.example.movieapp.listing.model.response.MoviesResponse;

import io.reactivex.Observable;

public class ListingRepositoryImpl implements ListingRepository {

    ApiService apiService;
    MovieStoreDatabase database;

    public ListingRepositoryImpl(ApiService apiService, MovieStoreDatabase database) {
        this.apiService = apiService;
        this.database = database;
    }

    @Override
    public Observable<MoviesResponse> fetchTrendingMovies(String page) {
        return apiService.getTrendingMovies(BuildConfig.API_KEY, page);
    }

    @Override
    public Observable<MoviesResponse> fetchNowPlayingMovies(String page) {
        return apiService.getNowPlayingMovies(BuildConfig.API_KEY, page);
    }

    @Override
    public LiveData<MoviesResponse> getSectionMoviesFromDb(SectionName sectionName) {
        return database.moviesDao().getMoviesOfSection(sectionName);
    }

    @Override
    public void syncDbWithServerResponseForSection(SectionName section, MoviesResponse response) {
        database.moviesDao().insertMovieEntity(new MoviesDbEntity(section, response));
    }
}
