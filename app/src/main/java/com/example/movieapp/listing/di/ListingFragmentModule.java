package com.example.movieapp.listing.di;

import com.example.movieapp.base.helper.ViewModelKey;
import com.example.movieapp.base.viewmodel.BaseViewModel;
import com.example.movieapp.listing.viewmodel.ListingViewModel;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class ListingFragmentModule {

    @IntoMap
    @Provides
    @ViewModelKey(value = ListingViewModel.class)
    BaseViewModel providesViewModel() {
        return new ListingViewModel();
    }

}
