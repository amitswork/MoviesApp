package com.example.movieapp.bookmarkscreen.viewmodel;

import static com.example.movieapp.bookmarkscreen.events.BookmarkScreenEvents.UPDATE_BOOKMARKED_MOVIES;
import static com.example.movieapp.listing.events.ListingEvents.API_ERROR;

import android.annotation.SuppressLint;

import com.example.movieapp.base.model.EventData;
import com.example.movieapp.base.viewmodel.BaseViewModel;
import com.example.movieapp.listing.constants.BookMarkItemType;
import com.example.movieapp.listing.helper.ListingResponseConverter;
import com.example.movieapp.listing.repository.BookmarkRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BookmarkFragmentViewModel extends BaseViewModel {

    BookmarkRepository bookmarkRepository;
    ListingResponseConverter converter;

    @Inject
    BookmarkFragmentViewModel(BookmarkRepository bookmarkRepository, ListingResponseConverter converter) {
        this.bookmarkRepository = bookmarkRepository;
        this.converter = converter;
    }

    @SuppressLint("CheckResult")
    public void fetchMovies() {
        Observable.fromCallable(() ->
                bookmarkRepository.getAllBookmarkedItems(BookMarkItemType.MOVIE)
        ).map(moviesList -> converter.convertToUiData(moviesList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itemList -> {
                    setEventStream(new EventData(UPDATE_BOOKMARKED_MOVIES, itemList));
                }, e -> {
                    setEventStream(new EventData(API_ERROR));
                });
    }

}
