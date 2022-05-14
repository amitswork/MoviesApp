package com.example.movieapp.common.network;

import static com.example.movieapp.common.constants.NetworkConstants.API_KEY_PARAM;
import static com.example.movieapp.common.constants.NetworkConstants.NOW_PLAYING_URL;
import static com.example.movieapp.common.constants.NetworkConstants.PAGE_PARAM;
import static com.example.movieapp.common.constants.NetworkConstants.QUERY_PARAM;
import static com.example.movieapp.common.constants.NetworkConstants.SEARCH_MOVIES_URL;
import static com.example.movieapp.common.constants.NetworkConstants.TRENDING_URL;

import com.example.movieapp.listing.model.response.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET(value = TRENDING_URL)
    public Observable<MoviesResponse> getTrendingMovies(
            @Query(API_KEY_PARAM) String apiKey,
            @Query(PAGE_PARAM) String page
    );

    @GET(value = NOW_PLAYING_URL)
    public Observable<MoviesResponse> getNowPlayingMovies(
            @Query(API_KEY_PARAM) String apiKey,
            @Query(PAGE_PARAM) String page
    );

    @GET(value = SEARCH_MOVIES_URL)
    public Observable<MoviesResponse> getSearchedMovies(
            @Query(API_KEY_PARAM) String apiKey,
            @Query(QUERY_PARAM) String query
    );

}
