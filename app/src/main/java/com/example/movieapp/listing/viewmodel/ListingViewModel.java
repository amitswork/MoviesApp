package com.example.movieapp.listing.viewmodel;

import static com.example.movieapp.listing.events.ListingEvents.API_ERROR;
import static com.example.movieapp.listing.events.ListingEvents.UPDATE_NOW_PLAYING_DATA;
import static com.example.movieapp.listing.events.ListingEvents.UPDATE_TRENDING_DATA;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;

import com.example.movieapp.base.model.EventData;
import com.example.movieapp.base.viewmodel.BaseViewModel;
import com.example.movieapp.listing.constants.BookMarkItemType;
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

    MoviesResponse trendingSectionResponse;
    int lastTrendingSectionPageRequested = 0;

    MoviesResponse nowPlayingSectionResponse;
    int lastNowPlayingSectionPageRequested = 0;

    HashMap<SectionName, ArrayList<MovieCardViewModel>> uiItemsMap = new HashMap<>();

    HashSet<String> bookmarkedMovieIds = new HashSet<>();


    public ListingViewModel(ListingRepository repository, ListingResponseConverter converter, BookmarkRepository bookmarkRepository) {
        this.repository = repository;
        this.converter = converter;
        this.bookmarkRepository = bookmarkRepository;
    }

    public void fetchTrendingMovies() {
        if (checkIfTrendingSectionAlreadyRequested()) {
            return;
        }
        lastTrendingSectionPageRequested ++;
        Disposable disposable = repository.fetchTrendingMovies(Integer.toString(lastTrendingSectionPageRequested))
                .map(response -> {
                    trendingSectionResponse = mergeResponse(trendingSectionResponse, response);
                    repository.syncDbWithServerResponseForSection(SectionName.TRENDING_SECTION, trendingSectionResponse);
                    return response;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResultData -> { }, e -> {
                    lastTrendingSectionPageRequested --;
                    setEventStream(new EventData(API_ERROR));
                });

        addDisposable(disposable);
    }

    private boolean checkIfTrendingSectionAlreadyRequested() {
        return (lastTrendingSectionPageRequested > 0 && trendingSectionResponse == null)
                || (trendingSectionResponse != null && lastTrendingSectionPageRequested > trendingSectionResponse.getPage());
    }

    public MoviesResponse getTrendingSectionResponse() {
        return trendingSectionResponse;
    }

    public void fetchNowPlayingMovies() {
        if (checkIfNowPlayingSectionAlreadyRequested()) {
            return;
        }
        lastNowPlayingSectionPageRequested ++;
        Disposable disposable = repository.fetchNowPlayingMovies(Integer.toString(lastNowPlayingSectionPageRequested))
                .map(response -> {
                    nowPlayingSectionResponse = mergeResponse(nowPlayingSectionResponse, response);
                    repository.syncDbWithServerResponseForSection(SectionName.NOW_PLAYING_SECTION, nowPlayingSectionResponse);
                    return response;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResultData -> { }, e -> {
                    lastNowPlayingSectionPageRequested --;
                    setEventStream(new EventData(API_ERROR));
                });

        addDisposable(disposable);
    }

    private boolean checkIfNowPlayingSectionAlreadyRequested() {
        return (lastNowPlayingSectionPageRequested > 0 && nowPlayingSectionResponse == null)
                || (nowPlayingSectionResponse != null && lastNowPlayingSectionPageRequested > nowPlayingSectionResponse.getPage());
    }

    public MoviesResponse getNowPlayingSectionResponse() {
        return nowPlayingSectionResponse;
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
        if (moviesResponses == null) {
            fetchDataFromServerFor(sectionName);
        } else {
            updateSectionResponse(sectionName, moviesResponses);
        }
    }

    void fetchDataFromServerFor(SectionName sectionName) {
        switch (sectionName) {
            case TRENDING_SECTION:
                fetchTrendingMovies();
                break;
            case NOW_PLAYING_SECTION:
                fetchNowPlayingMovies();
                break;
            default:
        }
    }

    void updateSectionResponse(SectionName sectionName, MoviesResponse moviesResponses) {
        ArrayList<MovieCardViewModel> convertedResponse = converter.convertToUiData(moviesResponses.getResults());
        uiItemsMap.put(sectionName, convertedResponse);
        updateBookmarkedCards();
        switch (sectionName) {
            case TRENDING_SECTION:
                trendingSectionResponse = moviesResponses;
                lastTrendingSectionPageRequested = moviesResponses.getPage();
                setEventStream(new EventData(UPDATE_TRENDING_DATA, convertedResponse));
                break;
            case NOW_PLAYING_SECTION:
                nowPlayingSectionResponse = moviesResponses;
                lastNowPlayingSectionPageRequested = moviesResponses.getPage();
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
        for (ArrayList<MovieCardViewModel> sectionCards : uiItemsMap.values()) {
            for (MovieCardViewModel card : sectionCards) {
                boolean isBookmarked = bookmarkedMovieIds.contains(card.data.getId());
                card.updateBookmark(isBookmarked);
            }
        }
    }


}
