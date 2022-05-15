package com.example.movieapp.details.di;

import com.example.movieapp.base.helper.ViewModelKey;
import com.example.movieapp.base.viewmodel.BaseViewModel;
import com.example.movieapp.details.viewmodel.DetailFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
interface DetailFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailFragmentViewModel.class)
    BaseViewModel providesViewModel(DetailFragmentViewModel viewModel);

}
