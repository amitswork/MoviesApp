package com.example.movieapp.listing.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;

import com.example.movieapp.database.MovieStoreDatabase;
import com.example.movieapp.database.entity.BookmarkEntity;
import com.example.movieapp.listing.constants.BookMarkItemType;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("CheckResult")
public class BookmarkRepositoryImpl implements BookmarkRepository {

    MovieStoreDatabase database;

    public BookmarkRepositoryImpl(MovieStoreDatabase database) {
        this.database = database;
    }

    @Override
    public void saveBookmarkedItem(String itemId) {
        Completable.fromRunnable(() -> {
            BookmarkEntity item = new BookmarkEntity(itemId, BookMarkItemType.MOVIE);
            database.bookmarkDao().insertItem(item);
        }).subscribeOn(Schedulers.io())
                .subscribe(() -> { }, e -> { });
    }

    @Override
    public void deleteBookmarkedItem(String item) {
        Completable.fromRunnable(() -> {
            database.bookmarkDao().deleteItem(item);
        }).subscribeOn(Schedulers.io())
                .subscribe(() -> { }, e -> { });
    }

    @Override
    public LiveData<List<String>> getAllBookmarkedItemIds(BookMarkItemType type) {
        return database.bookmarkDao().getAllItems(type);
    }

}
