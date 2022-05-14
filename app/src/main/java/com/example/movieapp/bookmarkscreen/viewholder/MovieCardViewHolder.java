package com.example.movieapp.bookmarkscreen.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.databinding.MovieCardLayoutBinding;
import com.example.movieapp.listing.viewmodel.MovieCardViewModel;

public class MovieCardViewHolder extends RecyclerView.ViewHolder {

    MovieCardLayoutBinding binding;

    public MovieCardViewHolder(MovieCardLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void onBind(MovieCardViewModel data) {
        binding.setModel(data);
        binding.executePendingBindings();
    }

}
