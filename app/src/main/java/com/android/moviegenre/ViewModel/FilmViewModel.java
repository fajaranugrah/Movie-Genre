package com.android.moviegenre.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.moviegenre.Connection.ApiCallTask.GetFilm;
import com.android.moviegenre.Connection.ApiCallTask.GetGenre;
import com.android.moviegenre.Model.Film;
import com.android.moviegenre.Model.FilmRepository;
import com.android.moviegenre.Model.Genre;
import com.android.moviegenre.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class FilmViewModel extends AndroidViewModel {

    private FilmRepository filmRepository;
    private LiveData<List<Film>> allFilms;
    private LiveData<List<Genre>> allGenres;
    Thread mThread;
    BottomSheetDialog bottomSheetDialog;
    public boolean update = false;
    List<Film> newData = new ArrayList<>();

    public FilmViewModel(@NonNull Application application) {
        super(application);
        filmRepository = new FilmRepository(application);
        allFilms = filmRepository.getAllFilms();
        allGenres = filmRepository.getAllGenres();
    }

    public void insert(Film film) {
        filmRepository.insert(film);
    }

    public void insertGenre(Genre genre) {
        filmRepository.insertGenre(genre);
    }

    public void update(Film film) {
        filmRepository.update(film);
    }

    public void delete(Film film) {
        filmRepository.delete(film);
    }

    public void deleteAllFilms() {
        filmRepository.deleteAll();
    }

    public LiveData<List<Film>> getAllFilms(){
        return allFilms;
    }

    public LiveData<List<Genre>> getAllGenres(){
        return allGenres;
    }

    public List<Film> Data() {
        return newData;
    }

    public void GetFilm(@NonNull final Activity activity, @NonNull final String page, @NonNull final String idGenre){
        try {
            new GetFilm(activity) {
                @Override
                public void OnApiResult(int statusCode, String failReason) {
                    if (statusCode == 1) {
                        //success
                        List<Film> firstData = new ArrayList<>();
                        firstData.addAll(getItems());
                        for (Film film : firstData){
                            if (page.equals("1")){
                                insert(film);
                            }
                        }
                        newData.addAll(getItems());
                    } else {
                        //failed
                        Toast.makeText(activity, "Problem on Your Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }.callApi(page, idGenre);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void GetGenre(@NonNull final Activity activity){
        try {
            new GetGenre(activity) {
                @Override
                public void OnApiResult(int statusCode, String failReason) {
                    if (statusCode == 1) {
                        //success
                        List<Genre> firstData = new ArrayList<>();
                        firstData.addAll(getItems());
                        for (Genre genre : firstData){
                            insertGenre(genre);
                        }
                    } else {
                        //failed
                        Toast.makeText(activity, "Problem on Your Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }.callApi();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isNetworkAvailable(@NonNull final Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public interface OnUpdateList{
        void updateList();
    }

    public void dismissDialog() {
        mThread = new Thread() {
            @Override
            public void run() {

                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    bottomSheetDialog.dismiss();
                } catch (Exception e) {
                    mThread.interrupt();
                    e.printStackTrace();
                }
            }
        };
        mThread.start();
    }

}
