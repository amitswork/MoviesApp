package com.example.movieapp.listing.viewmodel;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.example.movieapp.R;
import com.example.movieapp.listing.model.response.MovieResultData;
import com.example.movieapp.listing.repository.BookmarkRepository;

public class MovieCardViewModel {

    MovieResultData data;
    BookmarkRepository repository;

    ObservableBoolean isBookmarked = new ObservableBoolean(false);

    public ObservableField<Integer> bookmarkTint;

    public MovieCardViewModel(MovieResultData data, BookmarkRepository repository) {
        this.data = data;
        this.repository = repository;
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

    }

    public void toggleBookmark() {
        isBookmarked.set(!isBookmarked.get());
        updateBookmarkedItemInDB();
    }

    private void updateBookmarkedItemInDB() {
        if (isBookmarked.get()) {
            repository.saveBookmarkedItem(data.getId());
        } else {
            repository.deleteBookmarkedItem(data.getId());
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
