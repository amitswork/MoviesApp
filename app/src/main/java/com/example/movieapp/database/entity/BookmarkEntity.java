package com.example.movieapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.movieapp.listing.constants.BookMarkItemType;

@Entity(tableName = "BookmarkDb")
public class BookmarkEntity {

    @PrimaryKey
    @NonNull
    String itemId;
    BookMarkItemType type;

    public BookmarkEntity(String itemId, BookMarkItemType type) {
        this.itemId = itemId;
        this.type = type;
    }

    public String getItemId() {
        return itemId;
    }

    public BookMarkItemType getType() {
        return type;
    }
}