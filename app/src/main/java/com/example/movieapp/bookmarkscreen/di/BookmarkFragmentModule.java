package com.example.movieapp.bookmarkscreen.di;

import com.example.movieapp.base.helper.ViewModelKey;
import com.example.movieapp.base.viewmodel.BaseViewModel;
import com.example.movieapp.bookmarkscreen.viewmodel.BookmarkFragmentViewModel;
import com.example.movieapp.database.MovieStoreDatabase;
import com.example.movieapp.listing.repository.BookmarkRepository;
import com.example.movieapp.listing.repository.BookmarkRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
interface BookmarkFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(BookmarkFragmentViewModel.class)
    BaseViewModel providesViewModel(BookmarkFragmentViewModel viewModel);

    @Binds
    BookmarkRepository providesBookmarkRepository(BookmarkRepositoryImpl repository);

}
