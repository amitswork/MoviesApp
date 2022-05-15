package com.example.movieapp.listing.viewmodel;

import static com.example.movieapp.main.event.MainActivityEvents.OPEN_BOOKMARKS_PAGE;
import static com.example.movieapp.main.event.MainActivityEvents.OPEN_SEARCH_PAGE;

import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.base.model.EventData;

public class HeaderObservable {

    MutableLiveData<EventData> eventStream;

    public HeaderObservable(MutableLiveData<EventData> eventStream) {
        this.eventStream = eventStream;
    }

    public void onSearchIconClick() {
        eventStream.setValue(new EventData(OPEN_SEARCH_PAGE));
    }

    public void onBookmarkIconClick() {
        eventStream.setValue(new EventData(OPEN_BOOKMARKS_PAGE));
    }

}
