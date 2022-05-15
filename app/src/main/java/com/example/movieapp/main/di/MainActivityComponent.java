package com.example.movieapp.main.di;

import com.example.movieapp.base.di.BaseModule;
import com.example.movieapp.bookmarkscreen.di.BookmarkFragmentComponent;
import com.example.movieapp.details.di.DetailFragmentComponent;
import com.example.movieapp.listing.di.ListingFragmentComponent;
import com.example.movieapp.main.ui.MainActivity;
import com.example.movieapp.searchscreen.di.SearchFragmentComponent;

import dagger.Component;

@Component(modules = {BaseModule.class, MainActivityModule.class})
public interface MainActivityComponent {

    void inject(MainActivity activity);

    ListingFragmentComponent.Builder buildListingFragment();
    BookmarkFragmentComponent.Builder buildBookmarkFragment();
    SearchFragmentComponent.Builder buildSearchFragment();
    DetailFragmentComponent.Builder buildDetailFragment();

    @Component.Builder
    interface Builder {
        MainActivityComponent build();
    }

}
