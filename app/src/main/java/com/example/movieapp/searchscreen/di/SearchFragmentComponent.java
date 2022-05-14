package com.example.movieapp.searchscreen.di;

import com.example.movieapp.searchscreen.ui.SearchFragment;

import dagger.Subcomponent;

@Subcomponent(modules = SearchFragmentModule.class)
public interface SearchFragmentComponent {

    void inject(SearchFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        SearchFragmentComponent build();
    }

}