package com.android.moviegenre.Model;

import java.util.ArrayList;

public class ResultFilm {

    private String page;
    private String total_results;
    private String total_pages;
    private ArrayList<Film> results;

    public ResultFilm(String page, String total_results, String total_pages, ArrayList<Film> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public String getPage() {
        return page;
    }

    public String getTotal_results() {
        return total_results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public ArrayList<Film> getResults() {
        return results;
    }
}
