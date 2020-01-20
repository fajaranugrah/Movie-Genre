package com.android.moviegenre.Model;

import java.util.ArrayList;

public class ResultGenre {

    private ArrayList<Genre> genres;

    public ResultGenre(ArrayList<Genre> genre) {
        this.genres = genre;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }
}
