package com.example.movieapp.listing.model.response;

import static com.example.movieapp.common.constants.NetworkConstants.IMAGE_BASE_URL;

import com.google.gson.annotations.SerializedName;

public class MovieResultData {

    @SerializedName("id")
    String id;

    @SerializedName("poster_path")
    String posterPath;

    @SerializedName("title")
    String title;

    @SerializedName("release_date")
    String releaseDate;

    @SerializedName("overview")
    String overview;

    public String getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getImageUrl() {
        return IMAGE_BASE_URL + posterPath;
    }

}

