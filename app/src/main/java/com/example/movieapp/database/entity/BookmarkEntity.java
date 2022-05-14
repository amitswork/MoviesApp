package com.example.movieapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.movieapp.listing.constants.BookMarkItemType;
import com.example.movieapp.listing.model.response.MovieResultData;

@Entity(tableName = "BookmarkDb")
public class BookmarkEntity {

    @PrimaryKey
    @NonNull
    String itemId;

    MovieResultData data;
    BookMarkItemType type;

    public BookmarkEntity(MovieResultData data, BookMarkItemType type) {
        this.data = data;
        this.itemId = data.getId();
        this.type = type;
    }

    @NonNull
    public String getItemId() {
        return itemId;
    }

    public MovieResultData getData() {
        return data;
    }

    public BookMarkItemType getType() {
        return type;
    }
}