package com.example.movieapp.main.ui;

import static com.example.movieapp.main.event.MainActivityEvents.OPEN_BOOKMARKS_PAGE;
import static com.example.movieapp.main.event.MainActivityEvents.OPEN_DETAILS_PAGE;
import static com.example.movieapp.main.event.MainActivityEvents.OPEN_SEARCH_PAGE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.movieapp.R;
import com.example.movieapp.base.model.EventData;
import com.example.movieapp.bookmarkscreen.ui.BookmarkFragment;
import com.example.movieapp.databinding.ActivityMainBinding;
import com.example.movieapp.details.ui.DetailFragment;
import com.example.movieapp.listing.model.response.MovieResultData;
import com.example.movieapp.listing.ui.ListingFragment;
import com.example.movieapp.main.di.DaggerMainActivityComponent;
import com.example.movieapp.main.di.MainActivityComponent;
import com.example.movieapp.main.helper.IntentParser;
import com.example.movieapp.main.viewmodel.ActivitySharedViewModel;
import com.example.movieapp.searchscreen.ui.SearchFragment;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    MainActivityComponent component;

    ActivityMainBinding binding;

    ActivitySharedViewModel viewModel;

    @Inject
    IntentParser parser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependency();
        super.onCreate(savedInstanceState);
        viewModel = createViewModel();
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        viewModel.getEventStream().observe(this, this::handleEvents);
        parser.getEventStream().observe(this, this::handleEvents);
        parser.parseIntent(getIntent());
        openListingFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        parser.parseIntent(intent);
    }

    private void handleEvents(EventData event) {
        switch (event.getEventId()) {
            case OPEN_SEARCH_PAGE:
                openSearchFragment();
                break;
            case OPEN_BOOKMARKS_PAGE:
                openBookmarkFragment();
                break;
            case OPEN_DETAILS_PAGE:
                openDetailFragment((MovieResultData) event.getData());
                break;
            default:
        }
    }

    private void openSearchFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, SearchFragment.getInstance(), SearchFragment.TAG)
                .addToBackStack(SearchFragment.TAG)
                .commit();
    }

    private void openBookmarkFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, BookmarkFragment.getInstance(), BookmarkFragment.TAG)
                .addToBackStack(BookmarkFragment.TAG)
                .commit();
    }

    private void openDetailFragment(MovieResultData data) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, DetailFragment.getInstance(data), DetailFragment.TAG)
                .addToBackStack(DetailFragment.TAG)
                .commit();
    }

    private ActivitySharedViewModel createViewModel() {
        return new ViewModelProvider(this).get(ActivitySharedViewModel.class);
    }

    public MainActivityComponent getComponent() {
        return component;
    }

    private void openListingFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, ListingFragment.getInstance(), ListingFragment.TAG)
                .addToBackStack(ListingFragment.TAG)
                .commit();
    }

    private void injectDependency() {
        component = DaggerMainActivityComponent
                .builder()
                .build();
        component.inject(this);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }
}