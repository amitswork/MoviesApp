package com.example.movieapp.listing.viewmodel;

import static com.example.movieapp.listing.events.ListingEvents.BOOKMARK_ICON_CLICK;
import static com.example.movieapp.listing.events.ListingEvents.SEARCH_ICON_CLICK;

import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.base.model.EventData;

public class HeaderObservable {

    MutableLiveData<EventData> eventStream;

    public HeaderObservable(MutableLiveData<EventData> eventStream) {
        this.eventStream = eventStream;
    }

    public void onSearchIconClick() {
        eventStream.setValue(new EventData(SEARCH_ICON_CLICK));
    }

    public void onBookmarkIconClick() {
        eventStream.setValue(new EventData(BOOKMARK_ICON_CLICK));
    }

}
