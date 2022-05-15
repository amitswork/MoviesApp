package com.example.movieapp.common.util;

import com.example.movieapp.listing.model.response.MovieResultData;
import com.google.gson.Gson;

public class CommonUtil {

    public static String convertToJson(MovieResultData data) {
        Gson gson = new Gson();
        try {
            return gson.toJson(data);
        } catch (Exception e) {
            return null;
        }
    }

    public static MovieResultData convertToMovieResultData(String data) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(data, MovieResultData.class);
        } catch (Exception e) {
            return null;
        }
    }

}
