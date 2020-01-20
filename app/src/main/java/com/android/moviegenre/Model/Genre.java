package com.android.moviegenre.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "genre_table")
public class Genre {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public Genre(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}