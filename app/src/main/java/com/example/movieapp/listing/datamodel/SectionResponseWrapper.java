package com.example.movieapp.listing.datamodel;

import com.example.movieapp.listing.model.response.MoviesResponse;
import com.example.movieapp.listing.viewmodel.MovieCardViewModel;

import java.util.ArrayList;

public class SectionResponseWrapper {

    public int lastPageRequested = 0;
    public MoviesResponse lastFetchedResponse;

    public ArrayList<MovieCardViewModel> uiItems = new ArrayList<>();

}
