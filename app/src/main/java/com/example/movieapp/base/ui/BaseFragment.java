package com.example.movieapp.base.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.example.movieapp.base.model.EventData;
import com.example.movieapp.base.viewmodel.BaseViewModel;

public abstract class BaseFragment<T extends BaseViewModel, K extends ViewDataBinding> extends Fragment {

    protected T viewModel;
    protected K binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        injectDependency();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = createViewModel();
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        setDataBinding();
        initFragment();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getEventStream().observe(getViewLifecycleOwner(), this::handleEvents);
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.clearEventStream();
    }

    protected void handleEvents(EventData event) { }

    protected abstract void initFragment();

    protected abstract void setDataBinding();

    protected abstract Integer getLayoutId();

    protected abstract void injectDependency();

    protected abstract T createViewModel();
}
