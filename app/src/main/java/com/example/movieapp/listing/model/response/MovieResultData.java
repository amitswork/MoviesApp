package com.example.movieapp.listing.model.response;

import static com.example.movieapp.common.constants.NetworkConstants.IMAGE_BASE_URL;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieResultData implements Parcelable {

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

    protected MovieResultData(Parcel in) {
        id = in.readString();
        posterPath = in.readString();
        title = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
    }

    public static final Creator<MovieResultData> CREATOR = new Creator<MovieResultData>() {
        @Override
        public MovieResultData createFromParcel(Parcel in) {
            return new MovieResultData(in);
        }

        @Override
        public MovieResultData[] newArray(int size) {
            return new MovieResultData[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(posterPath);
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeString(overview);
    }
}

