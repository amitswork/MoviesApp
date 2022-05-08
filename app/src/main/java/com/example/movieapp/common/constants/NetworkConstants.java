package com.example.movieapp.common.constants;

import com.example.movieapp.BuildConfig;

public class NetworkConstants {
    public static final String BASE_URL = "https://api.themoviedb.org/";
    public static final String BASE_URL_V3 = BASE_URL + "3/";
    public static final String TRENDING_URL = BASE_URL_V3 + "movie/popular";
    public static final String NOW_PLAYING_URL = BASE_URL_V3 + "movie/now_playing";

    public static final String API_KEY_PARAM = "api_key";
    public static final String PAGE_PARAM = "page";

    public static final String IMAGE_BASE_URL1 = BASE_URL + "movie/%s/images" + "?" + API_KEY_PARAM + "=" + BuildConfig.API_KEY;
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
}
