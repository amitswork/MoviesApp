package com.example.movieapp.listing.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.databinding.MovieItemLayoutBinding;
import com.example.movieapp.listing.viewholder.MovieViewHolder;
import com.example.movieapp.listing.viewmodel.MovieCardViewModel;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    ArrayList<MovieCardViewModel> itemList;

    public MovieAdapter() {
        itemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieItemLayoutBinding binding = MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.onBind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateItemsList(ArrayList<MovieCardViewModel> items) {
        itemList.clear();
        itemList.addAll(items);
        notifyDataSetChanged();
    }
}
