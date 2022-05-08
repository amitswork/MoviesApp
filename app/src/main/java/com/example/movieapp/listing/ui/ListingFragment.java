package com.example.movieapp.listing.ui;

import static com.example.movieapp.listing.events.ListingEvents.API_ERROR;
import static com.example.movieapp.listing.events.ListingEvents.UPDATE_NOW_PLAYING_DATA;
import static com.example.movieapp.listing.events.ListingEvents.UPDATE_TRENDING_DATA;

import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movieapp.R;
import com.example.movieapp.base.model.EventData;
import com.example.movieapp.base.ui.BaseFragment;
import com.example.movieapp.base.viewmodel.ViewModelFactory;
import com.example.movieapp.common.customview.EndDividerItemDecorator;
import com.example.movieapp.databinding.FragmentListingLayoutBinding;
import com.example.movieapp.listing.adapter.MovieAdapter;
import com.example.movieapp.listing.helper.PaginationScroller;
import com.example.movieapp.listing.helper.SectionName;
import com.example.movieapp.listing.model.response.MovieResultData;
import com.example.movieapp.listing.model.response.MoviesResponse;
import com.example.movieapp.listing.viewmodel.ListingViewModel;
import com.example.movieapp.main.ui.MainActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ListingFragment extends BaseFragment<ListingViewModel, FragmentListingLayoutBinding> {

    public static String TAG = "ListingFragment";

    MovieAdapter trendingAdapter = new MovieAdapter();
    MovieAdapter nowPlayingAdapter = new MovieAdapter();

    @Inject
    ViewModelFactory factory;

    @Override
    protected void initFragment() {
        addSectionObservers();
    }

    private void addSectionObservers() {
        for (SectionName value : SectionName.values()) {
            viewModel.getSectionsData(value).observe(getViewLifecycleOwner(), moviesResponses -> {
                if (moviesResponses == null) {
                    viewModel.fetchDataFromServerFor(value);
                } else {
                    viewModel.updateSectionResponse(value, moviesResponses);
                    ArrayList<MovieResultData> convertedResponse = viewModel.convertToMoviesResultData(moviesResponses);
                    updateSection(value, convertedResponse);
                }
            });
        }
    }

    private void updateSection(SectionName value, ArrayList<MovieResultData> items) {
        switch (value) {
            case TRENDING_SECTION:
                trendingAdapter.updateItemsList(items);
                viewModel.showTrendingSection.set(trendingAdapter.getItemCount() > 0);
                break;
            case NOW_PLAYING_SECTION:
                nowPlayingAdapter.updateItemsList(items);
                viewModel.showNowPlayingSection.set(nowPlayingAdapter.getItemCount() > 0);
                break;
            default:
        }
    }

    void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void handleEvents(EventData event) {
        switch (event.getEventId()) {
            case API_ERROR:
                showToast(getResources().getString(R.string.api_error_message));
                break;
            default: break;
        }
    }

    @Override
    protected void setDataBinding() {
        binding.setModel(viewModel);
        bindTrendingRecyclerView();
        bindNowPlayingRecyclerView();
        binding.executePendingBindings();
    }

    private void bindTrendingRecyclerView() {
        EndDividerItemDecorator divider = new EndDividerItemDecorator(getResources().getDimensionPixelSize(R.dimen.margin_large));
        binding.trendingRecycler.addItemDecoration(divider);
        binding.setTrendingAdapter(trendingAdapter);
        binding.trendingRecycler.addOnScrollListener(new PaginationScroller((LinearLayoutManager) binding.trendingRecycler.getLayoutManager()) {
            @Override
            protected void loadMoreItems() {
                viewModel.fetchTrendingMovies();
            }

            @Override
            public boolean isLastPage() {
                return viewModel.getTrendingSectionResponse().isResponseComplete();
            }
        });
    }

    private void bindNowPlayingRecyclerView() {
        EndDividerItemDecorator divider = new EndDividerItemDecorator(getResources().getDimensionPixelSize(R.dimen.margin_large));
        binding.nowPlayingRecycler.addItemDecoration(divider);
        binding.setNowPlayingAdapter(nowPlayingAdapter);
        binding.nowPlayingRecycler.addOnScrollListener(new PaginationScroller((LinearLayoutManager) binding.nowPlayingRecycler.getLayoutManager()) {
            @Override
            protected void loadMoreItems() {
                viewModel.fetchNowPlayingMovies();
            }

            @Override
            public boolean isLastPage() {
                return viewModel.getNowPlayingSectionResponse().isResponseComplete();
            }
        });
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