package com.example.movieapp.listing.repository;

import androidx.lifecycle.LiveData;

import com.example.movieapp.listing.constants.BookMarkItemType;

import java.util.List;

public interface BookmarkRepository {
    void saveBookmarkedItem(String item);
    void deleteBookmarkedItem(String item);
    LiveData<List<String>> getAllBookmarkedItemIds(BookMarkItemType movie);
}