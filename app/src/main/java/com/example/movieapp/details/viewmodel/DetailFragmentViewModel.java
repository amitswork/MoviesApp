package com.example.movieapp.details.viewmodel;

import static com.example.movieapp.details.ui.DetailFragment.DETAIL_DATA;

import android.os.Bundle;

import com.example.movieapp.base.viewmodel.BaseViewModel;
import com.example.movieapp.listing.model.response.MovieResultData;

import javax.inject.Inject;

public class DetailFragmentViewModel extends BaseViewModel {

    public MovieResultData data;

    @Inject
    DetailFragmentViewModel() {

    }

    public void initData(Bundle bundle) {
        data = bundle.getParcelable(DETAIL_DATA);
    }

    public void onShareIconClick() {

    }

}
