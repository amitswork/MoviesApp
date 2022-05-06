package com.example.movieapp.base.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.base.model.EventData;

public class BaseViewModel extends ViewModel {

    final MutableLiveData<EventData> eventStream = new MutableLiveData<>();

    public MutableLiveData<EventData> getEventStream() {
        return eventStream;
    }

    public void clearEventStream() {
        eventStream.postValue(new EventData(""));
    }

}
