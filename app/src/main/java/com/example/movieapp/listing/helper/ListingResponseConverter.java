package com.example.movieapp.listing.helper;

import com.example.movieapp.listing.model.response.MovieResultData;
import com.example.movieapp.listing.model.response.MoviesResponse;

import java.util.ArrayList;

public class ListingResponseConverter {

    public ArrayList<MovieResultData> convertToUiData(MoviesResponse response) {
        return response == null ? null : response.getResults();
    }

}
