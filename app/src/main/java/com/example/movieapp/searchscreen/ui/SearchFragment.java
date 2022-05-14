package com.example.movieapp.searchscreen.ui;

import static com.example.movieapp.listing.events.ListingEvents.UPDATE_SEARCHED_MOVIES;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.lifecycle.ViewModelProvider;

import com.example.movieapp.R;
import com.example.movieapp.base.model.EventData;
import com.example.movieapp.base.ui.BaseFragment;
import com.example.movieapp.base.viewmodel.ViewModelFactory;
import com.example.movieapp.common.customview.BottomDividerItemDecoration;
import com.example.movieapp.databinding.SearchFragmentLayoutBinding;
import com.example.movieapp.listing.viewmodel.MovieCardViewModel;
import com.example.movieapp.main.ui.MainActivity;
import com.example.movieapp.searchscreen.adapter.SearchedItemsAdapter;
import com.example.movieapp.searchscreen.viewmodel.SearchFragmentViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

public class SearchFragment extends BaseFragment<SearchFragmentViewModel, SearchFragmentLayoutBinding> {

    public static String TAG = "SearchFragment";

    @Inject
    ViewModelFactory factory;

    SearchedItemsAdapter adapter = new SearchedItemsAdapter();

    @Override
    protected void initFragment() {
        viewModel.configureSearchText();
    }

    @Override
    protected void handleEvents(EventData event) {
        switch (event.getEventId()) {
            case UPDATE_SEARCHED_MOVIES:
                adapter.updateItemsList((ArrayList<MovieCardViewModel>) event.getData());
                break;
            default:
        }
    }

    @Override
    protected void setDataBinding() {
        binding.setModel(viewModel);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new BottomDividerItemDecoration(getResources().getDimensionPixelSize(R.dimen.margin_huge)));
        binding.searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = binding.searchView.getText() == null
                        ? null : binding.searchView.getText().toString();
                viewModel.fetchMovies(text);
            }
        });
    }

    @Override
    protected Integer getLayoutId() {
        return R.layout.search_fragment_layout;
    }

    @Override
    protected void injectDependency() {
        ((MainActivity) requireActivity())
                .getComponent()
                .buildSearchFragment()
                .build()
                .inject(this);
    }

    @Override
    protected SearchFragmentViewModel createViewModel() {
        return new ViewModelProvider(this, factory).get(SearchFragmentViewModel.class);
    }

    public static SearchFragment getInstance() {
        return new SearchFragment();
    }

}
