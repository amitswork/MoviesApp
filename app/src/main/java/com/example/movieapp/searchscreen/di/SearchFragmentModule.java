package com.example.movieapp.searchscreen.di;

import com.example.movieapp.base.helper.ViewModelKey;
import com.example.movieapp.base.viewmodel.BaseViewModel;
import com.example.movieapp.searchscreen.repository.SearchMoviesRepository;
import com.example.movieapp.searchscreen.repository.SearchMoviesRepositoryImpl;
import com.example.movieapp.searchscreen.viewmodel.SearchFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
interface SearchFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchFragmentViewModel.class)
    BaseViewModel providesViewModel(SearchFragmentViewModel viewModel);

    @Binds
    SearchMoviesRepository providesSearchRepository(SearchMoviesRepositoryImpl repository);

}