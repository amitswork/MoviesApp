package com.example.movieapp.listing.helper;

import com.example.movieapp.database.MovieStoreDatabase;
import com.example.movieapp.listing.model.response.MovieResultData;
import com.example.movieapp.listing.model.response.MoviesResponse;
import com.example.movieapp.listing.repository.BookmarkRepository;
import com.example.movieapp.listing.repository.BookmarkRepositoryImpl;
import com.example.movieapp.listing.viewmodel.MovieCardViewModel;

import java.util.ArrayList;

public class ListingResponseConverter {

    public ArrayList<MovieCardViewModel> convertToUiData(MoviesResponse response) {
        ArrayList<MovieCardViewModel> items = new ArrayList<>();
        MovieStoreDatabase database = MovieStoreDatabase.getInstance();
        for (MovieResultData movie : response.getResults()) {
            BookmarkRepository repository = new BookmarkRepositoryImpl(database);
            MovieCardViewModel card = new MovieCardViewModel(movie, repository);
            items.add(card);
        }
        return items;
    }

}
