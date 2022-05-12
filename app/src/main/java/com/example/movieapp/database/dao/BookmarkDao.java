package com.example.movieapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieapp.database.entity.BookmarkEntity;
import com.example.movieapp.listing.constants.BookMarkItemType;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(BookmarkEntity item);

    @Query("delete from BookmarkDb where itemId = :itemId")
    void deleteItem(String itemId);

    @Query("select itemId from BookmarkDb where type = :type")
    LiveData<List<String>> getAllItems(BookMarkItemType type);

}