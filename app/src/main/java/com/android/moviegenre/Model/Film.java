package com.android.moviegenre.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "film_table")
public class Film {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String original_title;
    private String poster_path;
    private String backdrop_path;
    private String release_date;
    private String overview;
    private String vote_average;

    public Film(String original_title, String poster_path, String backdrop_path, String release_date, String overview, String vote_average){
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.overview = overview;
        this.vote_average = vote_average;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }

    public String getVote_average() {
        return vote_average;
    }
}
