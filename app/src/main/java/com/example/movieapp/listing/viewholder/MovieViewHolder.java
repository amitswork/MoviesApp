package com.example.movieapp.listing.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.databinding.MovieCardWithBookmarkLayoutBinding;
import com.example.movieapp.listing.viewmodel.MovieCardViewModel;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    MovieCardWithBookmarkLayoutBinding binding;

    public MovieViewHolder(MovieCardWithBookmarkLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void onBind(MovieCardViewModel data) {
        binding.setModel(data);
        binding.executePendingBindings();
    }

}
