package com.example.movieapp.listing.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;

import com.example.movieapp.database.MovieStoreDatabase;
import com.example.movieapp.database.entity.BookmarkEntity;
import com.example.movieapp.listing.constants.BookMarkItemType;
import com.example.movieapp.listing.model.response.MovieResultData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("CheckResult")
public class BookmarkRepositoryImpl implements BookmarkRepository {

    MovieStoreDatabase database;

    @Inject
    public BookmarkRepositoryImpl(MovieStoreDatabase database) {
        this.database = database;
    }

    @Override
    public void saveBookmarkedItem(MovieResultData data) {
        Completable.fromRunnable(() -> {
            BookmarkEntity item = new BookmarkEntity(data, BookMarkItemType.MOVIE);
            database.bookmarkDao().insertItem(item);
        }).subscribeOn(Schedulers.io())
                .subscribe(() -> { }, e -> { });
    }

    @Override
    public void deleteBookmarkedItem(MovieResultData data) {
        Completable.fromRunnable(() -> {
            database.bookmarkDao().deleteItem(data.getId());
        }).subscribeOn(Schedulers.io())
                .subscribe(() -> { }, e -> { });
    }

    @Override
    public LiveData<List<MovieResultData>> getAllBookmarkedItemsLiveData(BookMarkItemType type) {
        return database.bookmarkDao().getAllItemsLiveData(type);
    }

    @Override
    public List<MovieResultData> getAllBookmarkedItems(BookMarkItemType type) {
        return database.bookmarkDao().getAllItems(type);
    }

}
