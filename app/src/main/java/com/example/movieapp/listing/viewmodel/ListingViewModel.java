package com.example.movieapp.listing.viewmodel;

import static com.example.movieapp.listing.events.ListingEvents.API_ERROR;
import static com.example.movieapp.listing.events.ListingEvents.UPDATE_NOW_PLAYING_DATA;
import static com.example.movieapp.listing.events.ListingEvents.UPDATE_TRENDING_DATA;

import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;

import com.example.movieapp.base.model.EventData;
import com.example.movieapp.base.viewmodel.BaseViewModel;
import com.example.movieapp.listing.constants.BookMarkItemType;
import com.example.movieapp.listing.datamodel.SectionResponseWrapper;
import com.example.movieapp.listing.helper.ListingResponseConverter;
import com.example.movieapp.listing.helper.SectionName;
import com.example.movieapp.listing.model.response.MovieResultData;
import com.example.movieapp.listing.model.response.MoviesResponse;
import com.example.movieapp.listing.repository.BookmarkRepository;
import com.example.movieapp.listing.repository.ListingRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListingViewModel extends BaseViewModel {

    ListingRepository repository;
    BookmarkRepository bookmarkRepository;
    ListingResponseConverter converter;

    public ObservableBoolean showTrendingSection = new ObservableBoolean(false);
    public ObservableBoolean showNowPlayingSection = new ObservableBoolean(false);

    HashMap<SectionName, SectionResponseWrapper> sectionDataMap = new HashMap<>();

    HashSet<String> bookmarkedMovieIds = new HashSet<>();


    public ListingViewModel(ListingRepository repository, ListingResponseConverter converter, BookmarkRepository bookmarkRepository) {
        this.repository = repository;
        this.converter = converter;
        this.bookmarkRepository = bookmarkRepository;
        initSectionsMap();
    }

    private void initSectionsMap() {
        for (SectionName section : SectionName.values()) {
            sectionDataMap.put(section, new SectionResponseWrapper());
        }
    }

    public void fetchMovies(SectionName section, Boolean replaceCache) {
        SectionResponseWrapper dataWrapper = sectionDataMap.get(section);
        if (dataWrapper == null || checkIfSectionAlreadyRequested(dataWrapper)) {
            return;
        }
        dataWrapper.lastPageRequested ++;
        Disposable disposable = repository.fetchMovies(section, Integer.toString(dataWrapper.lastPageRequested))
                .map(response -> {
                    if (replaceCache) {
                        dataWrapper.lastFetchedResponse = response;
                    } else {
                        dataWrapper.lastFetchedResponse = mergeResponse(dataWrapper.lastFetchedResponse, response);
                    }
                    repository.syncDbWithServerResponseForSection(section, dataWrapper.lastFetchedResponse);
                    return response;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResultData -> { }, e -> {
                    dataWrapper.lastPageRequested --;
                    //setEventStream(new EventData(API_ERROR));
                });

        addDisposable(disposable);
    }

    private boolean checkIfSectionAlreadyRequested(SectionResponseWrapper dataWrapper) {
        return (dataWrapper.lastPageRequested > 0 && dataWrapper.lastFetchedResponse == null)
                || (dataWrapper.lastFetchedResponse != null && dataWrapper.lastPageRequested > dataWrapper.lastFetchedResponse.getPage());
    }

    public MoviesResponse getSectionResponse(SectionName sectionName) {
        SectionResponseWrapper dataWrapper = sectionDataMap.get(sectionName);
        return dataWrapper == null ? null : dataWrapper.lastFetchedResponse;
    }

    public boolean isSectionComplete(SectionName sectionName) {
        MoviesResponse response = getSectionResponse(sectionName);
        return response != null && response.isResponseComplete();
    }

    private MoviesResponse mergeResponse(MoviesResponse oldResponse, MoviesResponse newResponse) {
        if (oldResponse == null) {
            return newResponse;
        } else {
            return oldResponse.mergeResponse(newResponse);
        }
    }

    public LiveData<MoviesResponse> getSectionsData(SectionName sectionName) {
        return repository.getSectionMoviesFromDb(sectionName);
    }

    public LiveData<List<MovieResultData>> getBookmarkedMovieIds() {
        return bookmarkRepository.getAllBookmarkedItemsLiveData(BookMarkItemType.MOVIE);
    }

    public void handleSectionsUpdate(SectionName sectionName, MoviesResponse moviesResponses) {
        if (moviesResponses != null) {
            updateSectionResponse(sectionName, moviesResponses);
        }
    }

    void updateSectionResponse(SectionName sectionName, MoviesResponse moviesResponses) {
        SectionResponseWrapper dataWrapper = sectionDataMap.get(sectionName);
        if (dataWrapper == null) return;
        ArrayList<MovieCardViewModel> convertedResponse = converter.convertToUiData(moviesResponses.getResults(), getEventStream());
        dataWrapper.uiItems = convertedResponse;
        dataWrapper.lastFetchedResponse = moviesResponses;
        dataWrapper.lastPageRequested = moviesResponses.getPage();

        updateBookmarkedCards();
        notifyUiUpdate(sectionName, convertedResponse);
    }

    private void notifyUiUpdate(SectionName sectionName, ArrayList<MovieCardViewModel> convertedResponse) {
        switch (sectionName) {
            case TRENDING_SECTION:
                setEventStream(new EventData(UPDATE_TRENDING_DATA, convertedResponse));
                break;
            case NOW_PLAYING_SECTION:
                setEventStream(new EventData(UPDATE_NOW_PLAYING_DATA, convertedResponse));
                break;
            default:
        }
    }

    public void handleBookmarkedMovies(List<MovieResultData> itemList) {
        List<String> bookmarkedMovieIds = new ArrayList<>();
        for (MovieResultData item : itemList) {
            bookmarkedMovieIds.add(item.getId());
        }

        updateBookmarkedMovieIds(bookmarkedMovieIds);
        updateBookmarkedCards();
    }

    void updateBookmarkedMovieIds(List<String> bookmarkedMovieIds) {
        this.bookmarkedMovieIds.clear();
        this.bookmarkedMovieIds.addAll(bookmarkedMovieIds);
    }

    private void updateBookmarkedCards() {
        for (SectionResponseWrapper sectionWrapper : sectionDataMap.values()) {
            ArrayList<MovieCardViewModel> sectionCards = sectionWrapper.uiItems;
            for (MovieCardViewModel card : sectionCards) {
                boolean isBookmarked = bookmarkedMovieIds.contains(card.data.getId());
                card.updateBookmark(isBookmarked);
            }
        }
    }


}
