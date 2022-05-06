package com.example.movieapp.main.di;

import com.example.movieapp.base.di.BaseModule;
import com.example.movieapp.listing.di.ListingFragmentComponent;
import com.example.movieapp.main.ui.MainActivity;

import dagger.Component;

@Component(modules = {BaseModule.class})
public interface MainActivityComponent {

    void inject(MainActivity activity);

    ListingFragmentComponent.Builder buildListingFragment();

    @Component.Builder
    interface Builder {
        MainActivityComponent build();
    }

}
