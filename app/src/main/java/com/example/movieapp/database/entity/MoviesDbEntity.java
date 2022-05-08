package com.example.movieapp.database.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.movieapp.listing.helper.SectionName;
import com.example.movieapp.listing.model.response.MoviesResponse;

@Entity(tableName = "MoviesDb",
        indices = {
        @Index(
                value = {"sectionName"}, unique = true
        )})
public class MoviesDbEntity {

    public MoviesDbEntity(SectionName sectionName, MoviesResponse moviesResponse) {
        this.sectionName = sectionName;
        this.moviesResponse = moviesResponse;
    }

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    public SectionName sectionName;

    public MoviesResponse moviesResponse;

}
