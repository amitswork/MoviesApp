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

    String apiKey = BuildConfig.API_KEY;

    public ListingRepositoryImpl(ApiService apiService, MovieStoreDatabase database) {
        this.apiService = apiService;
        this.database = database;
    }

    @Override
    public Observable<MoviesResponse> fetchMovies(SectionName section, String page) {
        switch (section) {
            case TRENDING_SECTION: return fetchTrendingMovies(page);
            case NOW_PLAYING_SECTION: return fetchNowPlayingMovies(page);
            default: return Observable.empty();
        }
    }

    @Override
    public Observable<MoviesResponse> fetchTrendingMovies(String page) {
        return apiService.getTrendingMovies(apiKey, page);
    }

    @Override
    public Observable<MoviesResponse> fetchNowPlayingMovies(String page) {
        return apiService.getNowPlayingMovies(apiKey, page);
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
