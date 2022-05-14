package com.example.movieapp.searchscreen.repository;

import com.example.movieapp.listing.model.response.MoviesResponse;

import io.reactivex.Observable;

public interface SearchMoviesRepository {

    Observable<MoviesResponse> searchMovies(String query);

}
