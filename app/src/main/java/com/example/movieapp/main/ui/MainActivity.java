package com.example.movieapp.main.ui;

import static com.example.movieapp.listing.events.ListingEvents.BOOKMARK_ICON_CLICK;
import static com.example.movieapp.listing.events.ListingEvents.SEARCH_ICON_CLICK;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.movieapp.R;
import com.example.movieapp.base.model.EventData;
import com.example.movieapp.bookmarkscreen.ui.BookmarkFragment;
import com.example.movieapp.databinding.ActivityMainBinding;
import com.example.movieapp.listing.ui.ListingFragment;
import com.example.movieapp.main.di.DaggerMainActivityComponent;
import com.example.movieapp.main.di.MainActivityComponent;
import com.example.movieapp.main.viewmodel.ActivitySharedViewModel;

public class MainActivity extends AppCompatActivity {

    MainActivityComponent component;

    ActivityMainBinding binding;

    ActivitySharedViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependency();
        super.onCreate(savedInstanceState);
        viewModel = createViewModel();
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        viewModel.getEventStream().observe(this, this::handleEvents);
        openListingFragment();
    }

    private void handleEvents(EventData event) {
        switch (event.getEventId()) {
            case SEARCH_ICON_CLICK:
                openSearchFragment();
                break;
            case BOOKMARK_ICON_CLICK:
                openBookmarkFragment();
                break;
            default:
        }
    }

    private void openSearchFragment() {

    }

    private void openBookmarkFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, BookmarkFragment.getInstance(), BookmarkFragment.TAG)
                .addToBackStack(BookmarkFragment.TAG)
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
                .replace(R.id.container, ListingFragment.getInstance(), ListingFragment.TAG)
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