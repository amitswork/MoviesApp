package com.example.movieapp.listing.viewmodel;

import static com.example.movieapp.main.event.MainActivityEvents.OPEN_DETAILS_PAGE;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.R;
import com.example.movieapp.base.model.EventData;
import com.example.movieapp.listing.model.response.MovieResultData;
import com.example.movieapp.listing.repository.BookmarkRepository;

public class MovieCardViewModel {

    MovieResultData data;
    BookmarkRepository repository;
    MutableLiveData<EventData> eventStream;

    ObservableBoolean isBookmarked = new ObservableBoolean(false);

    public ObservableField<Integer> bookmarkTint;

    public MovieCardViewModel(MovieResultData data, BookmarkRepository repository, MutableLiveData<EventData> eventStream) {
        this.data = data;
        this.repository = repository;
        this.eventStream = eventStream;

        bookmarkTint = new ObservableField<>(R.color.black);

        isBookmarked.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                bookmarkTint.set(getBookmarkTint(isBookmarked.get()));
            }
        });
    }

    public MovieResultData getData() {
        return data;
    }

    public void onCardClick() {
        eventStream.postValue(new EventData(OPEN_DETAILS_PAGE, data));
    }

    public void toggleBookmark() {
        isBookmarked.set(!isBookmarked.get());
        updateBookmarkedItemInDB();
    }

    private void updateBookmarkedItemInDB() {
        if (isBookmarked.get()) {
            repository.saveBookmarkedItem(data);
        } else {
            repository.deleteBookmarkedItem(data);
        }
    }

    int getBookmarkTint(boolean isSelected) {
        if (isSelected) {
            return R.color.red;
        } else {
            return R.color.black;
        }
    }

    public void updateBookmark(boolean value) {
        isBookmarked.set(value);
    }

}
