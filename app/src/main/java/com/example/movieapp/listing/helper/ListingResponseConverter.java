package com.example.movieapp.listing.helper;

import com.example.movieapp.database.MovieStoreDatabase;
import com.example.movieapp.listing.model.response.MovieResultData;
import com.example.movieapp.listing.repository.BookmarkRepository;
import com.example.movieapp.listing.repository.BookmarkRepositoryImpl;
import com.example.movieapp.listing.viewmodel.MovieCardViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ListingResponseConverter {

    @Inject
    ListingResponseConverter() {

    }

    public ArrayList<MovieCardViewModel> convertToUiData(List<MovieResultData> responseItems) {
        ArrayList<MovieCardViewModel> items = new ArrayList<>();
        MovieStoreDatabase database = MovieStoreDatabase.getInstance();
        for (MovieResultData movie : responseItems) {
            BookmarkRepository repository = new BookmarkRepositoryImpl(database);
            MovieCardViewModel card = new MovieCardViewModel(movie, repository);
            items.add(card);
        }
        return items;
    }

}
