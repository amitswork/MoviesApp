package com.example.movieapp.searchscreen.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.databinding.SearchedMovieItemLayoutBinding;
import com.example.movieapp.listing.viewmodel.MovieCardViewModel;

public class SearchedItemViewHolder extends RecyclerView.ViewHolder {

    SearchedMovieItemLayoutBinding binding;

    public SearchedItemViewHolder(SearchedMovieItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void onBind(MovieCardViewModel data) {
        binding.setModel(data);
        binding.executePendingBindings();
    }

}