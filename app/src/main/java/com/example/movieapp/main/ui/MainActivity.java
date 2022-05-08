package com.example.movieapp.main.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.movieapp.R;
import com.example.movieapp.databinding.ActivityMainBinding;
import com.example.movieapp.listing.ui.ListingFragment;
import com.example.movieapp.main.di.DaggerMainActivityComponent;
import com.example.movieapp.main.di.MainActivityComponent;

public class MainActivity extends AppCompatActivity {

    MainActivityComponent component;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependency();
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        openListingFragment();
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