package com.example.movieapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.movieapp.database.entity.MoviesDbEntity;
import com.example.movieapp.listing.helper.SectionName;
import com.example.movieapp.listing.model.response.MoviesResponse;

import java.util.List;

@Dao
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieEntity(MoviesDbEntity movie);

    @Query("SELECT moviesResponse FROM MoviesDb WHERE sectionName = :sectionName")
    LiveData<MoviesResponse> getMoviesOfSection(SectionName sectionName);


}
