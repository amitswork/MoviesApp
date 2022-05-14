package com.example.movieapp.bookmarkscreen.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.bookmarkscreen.viewholder.MovieCardViewHolder;
import com.example.movieapp.databinding.MovieCardLayoutBinding;
import com.example.movieapp.listing.viewmodel.MovieCardViewModel;

import java.util.ArrayList;

public class BookmarkCardAdapter extends RecyclerView.Adapter<MovieCardViewHolder> {

    ArrayList<MovieCardViewModel> itemList;

    public BookmarkCardAdapter() {
        itemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieCardLayoutBinding binding = MovieCardLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieCardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCardViewHolder holder, int position) {
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
