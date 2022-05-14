package com.example.movieapp.searchscreen.viewmodel;

import static com.example.movieapp.listing.events.ListingEvents.UPDATE_SEARCHED_MOVIES;

import android.annotation.SuppressLint;

import androidx.databinding.ObservableBoolean;

import com.example.movieapp.base.model.EventData;
import com.example.movieapp.base.viewmodel.BaseViewModel;
import com.example.movieapp.listing.helper.ListingResponseConverter;
import com.example.movieapp.listing.viewmodel.MovieCardViewModel;
import com.example.movieapp.searchscreen.repository.SearchMoviesRepository;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class SearchFragmentViewModel extends BaseViewModel {

    public final int SEARCH_TEXT_WAIT_TIME = 500;

    PublishSubject<String> publishSubject = PublishSubject.create();
    SearchMoviesRepository repository;
    ListingResponseConverter converter;

    public ObservableBoolean showLoader = new ObservableBoolean(false);
    public ObservableBoolean showError = new ObservableBoolean(false);

    @Inject
    SearchFragmentViewModel(SearchMoviesRepository repository, ListingResponseConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @SuppressLint("CheckResult")
    public void configureSearchText() {
        publishSubject
                .debounce(SEARCH_TEXT_WAIT_TIME, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .doOnEach(q -> showLoader.set(true))
                .switchMap(query -> repository.searchMovies(query))
                .doOnEach(result -> showLoader.set(false))
                .doOnError(this::handleError)
                .observeOn(Schedulers.io())
                .map(response -> converter.convertToUiData(response.getResults()))
                .retry()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, this::handleError);
    }

    public void fetchMovies(String query) {
        publishSubject.onNext(query);
    }

    private void handleSuccess(ArrayList<MovieCardViewModel> result) {
        setEventStream(new EventData(UPDATE_SEARCHED_MOVIES, result));
        updateDisplayError(result);
    }

    private void handleError(Throwable error) {
        ArrayList<MovieCardViewModel> items = new ArrayList<>();
        updateEventStream(new EventData(UPDATE_SEARCHED_MOVIES, items));
        updateDisplayError(items);
    }

    private void updateDisplayError(ArrayList<MovieCardViewModel> items) {
        if (items.size() == 0) {
            showError.set(true);
        } else {
            showError.set(false);
        }
    }
}
