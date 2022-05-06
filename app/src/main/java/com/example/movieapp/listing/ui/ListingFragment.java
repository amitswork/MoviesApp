package com.example.movieapp.listing.ui;

import androidx.lifecycle.ViewModelProvider;

import com.example.movieapp.R;
import com.example.movieapp.base.ui.BaseFragment;
import com.example.movieapp.base.viewmodel.ViewModelFactory;
import com.example.movieapp.databinding.FragmentListingLayoutBinding;
import com.example.movieapp.listing.viewmodel.ListingViewModel;
import com.example.movieapp.main.ui.MainActivity;

import javax.inject.Inject;

public class ListingFragment extends BaseFragment<ListingViewModel, FragmentListingLayoutBinding> {

    public static String TAG = "ListingFragment";

    @Inject
    ViewModelFactory factory;

    @Override
    protected void initFragment() {

    }

    @Override
    protected void setDataBinding() {

    }

    @Override
    protected Integer getLayoutId() {
        return R.layout.fragment_listing_layout;
    }

    @Override
    protected void injectDependency() {
        ((MainActivity) requireActivity())
                .getComponent()
                .buildListingFragment()
                .build()
                .inject(this);
    }

    @Override
    protected ListingViewModel createViewModel() {
        return new ViewModelProvider(this, factory).get(ListingViewModel.class);
    }

    public static ListingFragment getInstance() {
        return new ListingFragment();
    }

}
