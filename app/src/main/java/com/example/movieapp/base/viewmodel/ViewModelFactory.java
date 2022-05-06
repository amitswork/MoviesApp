package com.example.movieapp.base.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Provider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    Map<Class<? extends ViewModel>, Provider<BaseViewModel>> viewModels;

    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<BaseViewModel>> viewModels) {
        this.viewModels = viewModels;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) viewModels.get(modelClass).get();
    }
}
