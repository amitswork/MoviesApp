package com.example.movieapp.listing.viewmodel;

import static com.example.movieapp.listing.events.ListingEvents.API_ERROR;
import static com.example.movieapp.listing.events.ListingEvents.UPDATE_NOW_PLAYING_DATA;
import static com.example.movieapp.listing.events.ListingEvents.UPDATE_TRENDING_DATA;

import static java.sql.DriverManager.println;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;

import com.example.movieapp.base.model.EventData;
import com.example.movieapp.base.viewmodel.BaseViewModel;
import com.example.movieapp.listing.helper.ListingResponseConverter;
import com.example.movieapp.listing.helper.SectionName;
import com.example.movieapp.listing.model.response.MovieResultData;
import com.example.movieapp.listing.model.response.MoviesResponse;
import com.example.movieapp.listing.repository.ListingRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ListingViewModel extends BaseViewModel {

    ListingRepository repository;
    ListingResponseConverter converter;

    public ObservableBoolean showTrendingSection = new ObservableBoolean(false);
    public ObservableBoolean showNowPlayingSection = new ObservableBoolean(false);

    private MoviesResponse trendingSectionResponse;
    private int lastTrendingSectionPageRequested = 0;

    private MoviesResponse nowPlayingSectionResponse;
    private int lastNowPlayingSectionPageRequested = 0;

    public ListingViewModel(ListingRepository repository, ListingResponseConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public void fetchTrendingMovies() {
        if (checkIfTrendingSectionAlreadyRequested()) {
            return;
        }
        lastTrendingSectionPageRequested ++;
        Disposable disposable = repository.fetchTrendingMovies(Integer.toString(lastTrendingSectionPageRequested))
                .map(response -> {
                    trendingSectionResponse = mergeResponse(trendingSectionResponse, response);
                    println("page - "+lastTrendingSectionPageRequested + " - " + trendingSectionResponse.getResults().size());
                    repository.syncDbWithServerResponseForSection(SectionName.TRENDING_SECTION, trendingSectionResponse);
                    return converter.convertToUiData(response);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResultData -> { }, e -> {
                    lastTrendingSectionPageRequested --;
                    updateEventStream(new EventData(API_ERROR));
                });

        addDisposable(disposable);
    }

    private boolean checkIfTrendingSectionAlreadyRequested() {
        return lastTrendingSectionPageRequested > 0 &&
                lastTrendingSectionPageRequested > trendingSectionResponse.getPage();
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
                    return converter.convertToUiData(response);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResultData -> { }, e -> {
                    lastNowPlayingSectionPageRequested --;
                    updateEventStream(new EventData(API_ERROR));
                });

        addDisposable(disposable);
    }

    private boolean checkIfNowPlayingSectionAlreadyRequested() {
        return lastNowPlayingSectionPageRequested > 0 &&
                lastNowPlayingSectionPageRequested > nowPlayingSectionResponse.getPage();
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

    public ArrayList<MovieResultData> convertToMoviesResultData(MoviesResponse moviesResponses) {
        return converter.convertToUiData(moviesResponses);
    }

    public void updateSectionResponse(SectionName sectionName, MoviesResponse moviesResponses) {
        switch (sectionName) {
            case TRENDING_SECTION:
                trendingSectionResponse = moviesResponses;
                lastTrendingSectionPageRequested = moviesResponses.getPage();
                break;
            case NOW_PLAYING_SECTION:
                nowPlayingSectionResponse = moviesResponses;
                lastNowPlayingSectionPageRequested = moviesResponses.getPage();
                break;
            default:
        }
    }

    public void fetchDataFromServerFor(SectionName sectionName) {
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
}
