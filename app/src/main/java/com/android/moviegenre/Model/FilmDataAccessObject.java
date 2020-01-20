package com.android.moviegenre.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FilmDataAccessObject {

    @Insert
    void insert(Film film);
    @Insert
    void insertGenre(Genre film);
    @Update
    void update(Film film);
    @Delete
    void delete(Film film);

    @Query("DELETE FROM film_table")
    void deleteAllFilm();

    @Query("SELECT * FROM film_table")
    LiveData<List<Film>> getAllFilms();

    @Query("SELECT * FROM genre_table")
    LiveData<List<Genre>> getAllGenres();
}
