package com.example.movieapp.database.converter;

import androidx.room.TypeConverter;

import com.example.movieapp.listing.model.response.MovieResultData;
import com.example.movieapp.listing.model.response.MoviesResponse;
import com.google.gson.Gson;

public class MovieDataConverter {

    private final Gson gson = new Gson();

    @TypeConverter
    public String toString(MoviesResponse response) {
        return gson.toJson(response);
    }

    @TypeConverter
    public MoviesResponse fromMoviesResponse(String value) {
        return value == null ? null : gson.fromJson(value, MoviesResponse.class);
    }

    @TypeConverter
    public String toString(MovieResultData response) {
        return gson.toJson(response);
    }

    @TypeConverter
    public MovieResultData fromMovieResultDataToString(String value) {
        return value == null ? null : gson.fromJson(value, MovieResultData.class);
    }

}
