package com.example.movieapp.listing.di;

import com.example.movieapp.base.helper.ViewModelKey;
import com.example.movieapp.base.viewmodel.BaseViewModel;
import com.example.movieapp.common.network.ApiService;
import com.example.movieapp.common.network.RetrofitClient;
import com.example.movieapp.database.MovieStoreDatabase;
import com.example.movieapp.listing.helper.ListingResponseConverter;
import com.example.movieapp.listing.repository.ListingRepository;
import com.example.movieapp.listing.repository.ListingRepositoryImpl;
import com.example.movieapp.listing.viewmodel.ListingViewModel;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class ListingFragmentModule {

    @IntoMap
    @Provides
    @ViewModelKey(value = ListingViewModel.class)
    BaseViewModel providesViewModel(ListingRepository repository, ListingResponseConverter converter) {
        return new ListingViewModel(repository, converter);
    }

    @Provides
    ApiService providesApiService() {
        return RetrofitClient.getInstance()
                .create(ApiService.class);
    }

    @Provides
    ListingRepository providesListingRepository(ApiService apiService, MovieStoreDatabase database) {
        return new ListingRepositoryImpl(apiService, database);
    }

    @Provides
    ListingResponseConverter providesListingConverter() {
        return new ListingResponseConverter();
    }

    @Provides
    MovieStoreDatabase providesMovieDatabase() {
        return MovieStoreDatabase.getInstance();
    }


}
