package com.android.moviegenre.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FilmRepository {
    private FilmDataAccessObject filmDataAccessObject;
    private LiveData<List<Film>> allFilms;
    private LiveData<List<Genre>> allGenres;

    public FilmRepository(Application application) {
        FilmDatabase filmDatabase = FilmDatabase.getInstance(application);
        filmDataAccessObject = filmDatabase.filmDataAccessObject();
        allFilms = filmDataAccessObject.getAllFilms();
        allGenres = filmDataAccessObject.getAllGenres();
    }

    public void insert(Film film) {
        new InsertFilmAsyncTask(filmDataAccessObject).execute(film);
    }

    public void insertGenre(Genre genre) {
        new InsertGenreAsyncTask(filmDataAccessObject).execute(genre);
    }

    public  void update(Film film) {
        new UpdateFilmAsyncTask(filmDataAccessObject).execute(film);
    }

    public void delete(Film film) {
        new DeleteFilmAsyncTask(filmDataAccessObject).execute(film);
    }

    public void deleteAll() {
        new DeleteAlltFilmsAsyncTask(filmDataAccessObject).execute();
    }

    public LiveData<List<Film>> getAllFilms() {
        return allFilms;
    }

    public LiveData<List<Genre>> getAllGenres() {
        return allGenres;
    }

    private static class InsertFilmAsyncTask extends AsyncTask<Film, Void, Void> {
        private FilmDataAccessObject filmDataAccessObject;

        private InsertFilmAsyncTask(FilmDataAccessObject filmDataAccessObject) {
            this.filmDataAccessObject = filmDataAccessObject;
        }

        @Override
        protected Void doInBackground(Film... films) {
            filmDataAccessObject.insert(films[0]);
            return null;
        }
    }

    private static class InsertGenreAsyncTask extends AsyncTask<Genre, Void, Void> {
        private FilmDataAccessObject filmDataAccessObject;

        private InsertGenreAsyncTask(FilmDataAccessObject filmDataAccessObject) {
            this.filmDataAccessObject = filmDataAccessObject;
        }

        @Override
        protected Void doInBackground(Genre... genres) {
            filmDataAccessObject.insertGenre(genres[0]);
            return null;
        }
    }

    private static class UpdateFilmAsyncTask extends AsyncTask<Film, Void, Void> {
        private FilmDataAccessObject filmDataAccessObject;

        private UpdateFilmAsyncTask(FilmDataAccessObject filmDataAccessObject) {
            this.filmDataAccessObject = filmDataAccessObject;
        }

        @Override
        protected Void doInBackground(Film... films) {
            filmDataAccessObject.update(films[0]);
            return null;
        }
    }

    private static class DeleteFilmAsyncTask extends AsyncTask<Film, Void, Void> {
        private FilmDataAccessObject filmDataAccessObject;

        private DeleteFilmAsyncTask(FilmDataAccessObject filmDataAccessObject) {
            this.filmDataAccessObject = filmDataAccessObject;
        }

        @Override
        protected Void doInBackground(Film... films) {
            filmDataAccessObject.delete(films[0]);
            return null;
        }
    }

    private static class DeleteAlltFilmsAsyncTask extends AsyncTask<Void, Void, Void> {
        private FilmDataAccessObject filmDataAccessObject;

        private DeleteAlltFilmsAsyncTask(FilmDataAccessObject filmDataAccessObject) {
            this.filmDataAccessObject = filmDataAccessObject;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            filmDataAccessObject.deleteAllFilm();
            return null;
        }
    }
}
