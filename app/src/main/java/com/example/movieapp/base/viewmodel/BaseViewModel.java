package com.example.movieapp.base.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.base.model.EventData;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel {

    final MutableLiveData<EventData> eventStream = new MutableLiveData<>();
    final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<EventData> getEventStream() {
        return eventStream;
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    protected void updateEventStream(EventData eventData) {
        eventStream.postValue(eventData);
    }

    public void clearEventStream() {
        eventStream.postValue(new EventData(""));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
