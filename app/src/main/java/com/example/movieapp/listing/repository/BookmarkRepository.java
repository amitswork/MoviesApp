package com.example.movieapp.listing.repository;

import androidx.lifecycle.LiveData;

import com.example.movieapp.listing.constants.BookMarkItemType;
import com.example.movieapp.listing.model.response.MovieResultData;

import java.util.List;

public interface BookmarkRepository {
    void saveBookmarkedItem(MovieResultData data);
    void deleteBookmarkedItem(MovieResultData data);
    LiveData<List<MovieResultData>> getAllBookmarkedItemsLiveData(BookMarkItemType movie);
    List<MovieResultData> getAllBookmarkedItems(BookMarkItemType movie);
}