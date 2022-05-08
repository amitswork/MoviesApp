package com.example.movieapp.listing.repository;

import androidx.lifecycle.LiveData;

import com.example.movieapp.listing.helper.SectionName;
import com.example.movieapp.listing.model.response.MoviesResponse;

import io.reactivex.Observable;

public interface ListingRepository {
    public Observable<MoviesResponse> fetchTrendingMovies(String page);
    public Observable<MoviesResponse> fetchNowPlayingMovies(String page);

    public LiveData<MoviesResponse> getSectionMoviesFromDb(SectionName sectionName);
    public void syncDbWithServerResponseForSection(SectionName section, MoviesResponse response);
}
