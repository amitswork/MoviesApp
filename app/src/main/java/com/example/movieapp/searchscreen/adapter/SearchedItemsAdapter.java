package com.example.movieapp.searchscreen.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.databinding.SearchedMovieItemLayoutBinding;
import com.example.movieapp.listing.viewmodel.MovieCardViewModel;
import com.example.movieapp.searchscreen.viewholder.SearchedItemViewHolder;

import java.util.ArrayList;

public class SearchedItemsAdapter extends RecyclerView.Adapter<SearchedItemViewHolder> {

    ArrayList<MovieCardViewModel> itemList;

    public SearchedItemsAdapter() {
        itemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SearchedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchedMovieItemLayoutBinding binding = SearchedMovieItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchedItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedItemViewHolder holder, int position) {
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
