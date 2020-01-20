package com.android.moviegenre.Connection;

import com.android.moviegenre.Model.ResultFilm;
import com.android.moviegenre.Model.ResultGenre;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/3/discover/movie")
    Call<ResultFilm> doGetFilmList(
            @Query("with_genres") String sort_by,
            @Query("page") String page,
            @Query("api_key") String api_key
    );

    @GET("/3/genre/movie/list")
    Call<ResultGenre> doGetGenreList(
            @Query("api_key") String api_key
    );
}
