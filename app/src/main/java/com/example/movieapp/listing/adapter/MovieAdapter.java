package com.example.movieapp.listing.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.databinding.MovieItemLayoutBinding;
import com.example.movieapp.listing.model.response.MovieResultData;
import com.example.movieapp.listing.viewholder.MovieViewHolder;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    ArrayList<MovieResultData> itemList;

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

    public void addItemsList(ArrayList<MovieResultData> items) {
        int oldCount = itemList.size();
        itemList.addAll(items);
        int newCount = itemList.size();
        notifyItemRangeInserted(oldCount, newCount);
    }

    public void updateItemsList(ArrayList<MovieResultData> items) {
        itemList.clear();
        itemList.addAll(items);
        notifyDataSetChanged();
    }
}
