package com.example.movieapp.listing.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.databinding.MovieItemLayoutBinding;
import com.example.movieapp.listing.model.response.MovieResultData;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    MovieItemLayoutBinding binding;

    public MovieViewHolder(MovieItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void onBind(MovieResultData data) {
        binding.setModel(data);
        binding.executePendingBindings();
    }

}
