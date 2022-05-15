package com.example.movieapp.details.ui;

import static com.example.movieapp.common.constants.PagePaths.BASE_PATH;
import static com.example.movieapp.common.constants.PagePaths.DATA_PARAM;
import static com.example.movieapp.common.constants.PagePaths.DATA_PARAM_KEY;
import static com.example.movieapp.common.constants.PagePaths.DETAIL_PAGE_PATH;
import static com.example.movieapp.common.constants.PagePaths.START_QUERY_PARAM;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.example.movieapp.R;
import com.example.movieapp.base.model.EventData;
import com.example.movieapp.base.ui.BaseFragment;
import com.example.movieapp.base.viewmodel.ViewModelFactory;
import com.example.movieapp.common.util.CommonUtil;
import com.example.movieapp.databinding.DetailFragmentLayoutBinding;
import com.example.movieapp.details.viewmodel.DetailFragmentViewModel;
import com.example.movieapp.listing.model.response.MovieResultData;
import com.example.movieapp.main.ui.MainActivity;

import javax.inject.Inject;

public class DetailFragment extends BaseFragment<DetailFragmentViewModel, DetailFragmentLayoutBinding> {

    public static String TAG = "DetailFragment";
    public static final String DETAIL_DATA = "DETAIL_DATA";

    @Inject
    ViewModelFactory factory;

    @Override
    protected void initFragment() {
        if (getArguments() != null) {
            viewModel.initData(getArguments());
        }
    }

    @Override
    protected void handleEvents(EventData event) {
        switch (event.getEventId()) {
            default: sendEventToActivity(event);
        }
    }

    private void onShareClick() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        String deeplink = createDetailDeeplink();

        intent.putExtra(Intent.EXTRA_TEXT, deeplink);
        startActivity(intent);
    }

    private String createDetailDeeplink() {
        String jsonData = CommonUtil.convertToJson(viewModel.data);
        String data = Uri.encode(jsonData);
        return BASE_PATH + DETAIL_PAGE_PATH + START_QUERY_PARAM + DATA_PARAM + data;
    }

    @Override
    protected void setDataBinding() {
        binding.setModel(viewModel);
        binding.shareIcon.setOnClickListener(view -> onShareClick());
    }

    @Override
    protected Integer getLayoutId() {
        return R.layout.detail_fragment_layout;
    }

    @Override
    protected void injectDependency() {
        ((MainActivity) requireActivity())
                .getComponent()
                .buildDetailFragment()
                .build()
                .inject(this);
    }

    @Override
    protected DetailFragmentViewModel createViewModel() {
        return new ViewModelProvider(this, factory).get(DetailFragmentViewModel.class);
    }

    public static DetailFragment getInstance(MovieResultData data) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DETAIL_DATA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

}