package com.example.movieapp.details.di;

import com.example.movieapp.details.ui.DetailFragment;

import dagger.Subcomponent;

@Subcomponent(modules = DetailFragmentModule.class)
public interface DetailFragmentComponent {

    void inject(DetailFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        DetailFragmentComponent build();
    }

}