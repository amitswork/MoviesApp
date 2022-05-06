package com.example.movieapp.listing.di;

import com.example.movieapp.base.di.BaseModule;
import com.example.movieapp.listing.ui.ListingFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {ListingFragmentModule.class, BaseModule.class})
public interface ListingFragmentComponent {

    void inject(ListingFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        ListingFragmentComponent build();
    }

}
