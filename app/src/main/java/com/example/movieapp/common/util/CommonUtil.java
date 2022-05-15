package com.example.movieapp.common.util;

import static com.example.movieapp.common.constants.PagePaths.BASE_PATH;
import static com.example.movieapp.common.constants.PagePaths.DATA_PARAM;
import static com.example.movieapp.common.constants.PagePaths.DETAIL_PAGE_PATH;
import static com.example.movieapp.common.constants.PagePaths.START_QUERY_PARAM;

import android.net.Uri;

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

    public static String createDetailDeeplink(MovieResultData data) {
        String jsonData = CommonUtil.convertToJson(data);
        String json = Uri.encode(jsonData);
        return BASE_PATH + DETAIL_PAGE_PATH + START_QUERY_PARAM + DATA_PARAM + json;
    }
}
