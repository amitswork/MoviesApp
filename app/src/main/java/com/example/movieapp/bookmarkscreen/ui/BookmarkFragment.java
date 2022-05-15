package com.example.movieapp.bookmarkscreen.ui;

import static com.example.movieapp.bookmarkscreen.events.BookmarkScreenEvents.UPDATE_BOOKMARKED_MOVIES;

import androidx.lifecycle.ViewModelProvider;

import com.example.movieapp.R;
import com.example.movieapp.base.model.EventData;
import com.example.movieapp.base.ui.BaseFragment;
import com.example.movieapp.base.viewmodel.ViewModelFactory;
import com.example.movieapp.bookmarkscreen.adapter.BookmarkCardAdapter;
import com.example.movieapp.bookmarkscreen.viewmodel.BookmarkFragmentViewModel;
import com.example.movieapp.common.customview.EndDividerItemDecorator;
import com.example.movieapp.databinding.BookmarkFragmentLayoutBinding;
import com.example.movieapp.listing.viewmodel.MovieCardViewModel;
import com.example.movieapp.main.ui.MainActivity;

import java.util.ArrayList;

import javax.inject.Inject;

public class BookmarkFragment extends BaseFragment<BookmarkFragmentViewModel, BookmarkFragmentLayoutBinding> {

    public static String TAG = "BookmarkFragment";

    @Inject
    ViewModelFactory factory;

    BookmarkCardAdapter adapter = new BookmarkCardAdapter();

    @Override
    protected void initFragment() {
        viewModel.fetchMovies();
    }

    @Override
    protected void handleEvents(EventData event) {
        switch (event.getEventId()) {
            case UPDATE_BOOKMARKED_MOVIES:
                adapter.updateItemsList((ArrayList<MovieCardViewModel>) event.getData());
                break;
            default: sendEventToActivity(event);
        }
    }

    @Override
    protected void setDataBinding() {
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new EndDividerItemDecorator(getResources().getDimensionPixelSize(R.dimen.margin_huge)));
    }

    @Override
    protected Integer getLayoutId() {
        return R.layout.bookmark_fragment_layout;
    }

    @Override
    protected void injectDependency() {
        ((MainActivity) requireActivity())
                .getComponent()
                .buildBookmarkFragment()
                .build()
                .inject(this);
    }

    @Override
    protected BookmarkFragmentViewModel createViewModel() {
        return new ViewModelProvider(this, factory).get(BookmarkFragmentViewModel.class);
    }

    public static BookmarkFragment getInstance() {
        return new BookmarkFragment();
    }

}
