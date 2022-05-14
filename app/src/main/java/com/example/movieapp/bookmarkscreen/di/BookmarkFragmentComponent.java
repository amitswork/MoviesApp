package com.example.movieapp.bookmarkscreen.di;

import com.example.movieapp.bookmarkscreen.ui.BookmarkFragment;

import dagger.Subcomponent;

@Subcomponent(modules = BookmarkFragmentModule.class)
public interface BookmarkFragmentComponent {

    void inject(BookmarkFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        BookmarkFragmentComponent build();
    }

}
