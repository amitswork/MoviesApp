package com.example.movieapp.listing.model.response;

import java.util.ArrayList;

public class MoviesResponse {

    Integer page;
    ArrayList<MovieResultData> results;
    Integer total_pages;
    Integer total_results;

    public ArrayList<MovieResultData> getResults() {
        return results;
    }

    public Integer getPage() {
        return page;
    }

    public MoviesResponse mergeResponse(MoviesResponse response) {
        page = response.page;
        results.addAll(response.results);
        return this;
    }

    public boolean isResponseComplete() {
        return page.equals(total_pages);
    }

}
