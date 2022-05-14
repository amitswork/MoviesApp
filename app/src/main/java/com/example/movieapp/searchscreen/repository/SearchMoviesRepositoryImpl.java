package com.example.movieapp.searchscreen.repository;

import com.example.movieapp.BuildConfig;
import com.example.movieapp.common.network.ApiService;
import com.example.movieapp.listing.model.response.MoviesResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SearchMoviesRepositoryImpl implements SearchMoviesRepository {

    ApiService apiService;

    String apiKey = BuildConfig.API_KEY;

    @Inject
    public SearchMoviesRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<MoviesResponse> searchMovies(String query) {
        return apiService.getSearchedMovies(apiKey, query)
                .subscribeOn(Schedulers.io());
    }
}
