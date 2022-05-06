package com.example.movieapp.base.di;

import androidx.lifecycle.ViewModel;

import com.example.movieapp.base.viewmodel.BaseViewModel;
import com.example.movieapp.base.viewmodel.ViewModelFactory;

import java.util.Map;

import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseModule {

    @Provides
    ViewModelFactory providesViewModelFactory(Map<Class<? extends ViewModel>, Provider<BaseViewModel>> viewModels) {
        return new ViewModelFactory(viewModels);
    }

}
