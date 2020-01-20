package com.android.moviegenre.Connection.ApiCallTask;

import android.app.Activity;
import android.util.Log;

import com.android.moviegenre.Connection.APIClient;
import com.android.moviegenre.Connection.APIInterface;
import com.android.moviegenre.Model.Film;
import com.android.moviegenre.Model.ResultFilm;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class GetFilm extends ApiCalling {

    Activity activity;
    ResultFilm resultFilm;
    ArrayList<Film> items = new ArrayList<>();

    public GetFilm(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void callApi(String... params){

        //default limit of 10 hits per minute
        String page = params[0];
        String genre = params[1];

        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .doGetFilmList(genre, page, "f7b67d9afdb3c971d4419fa4cb667fbf")
                    .enqueue(new Callback<ResultFilm>() {
                        @Override
                        public void onResponse(Call<ResultFilm> call, Response<ResultFilm> response) {
                            switch (response.code()){
                                case 200:
                                    try {
                                        resultFilm = response.body();
                                        for (int i = 0; i<resultFilm.getResults().size(); i++) {
                                            if (i!=10){
                                                items.add(resultFilm.getResults().get(i));
                                            } else {
                                                break;
                                            }
                                        }
                                        Log.e("CheckBody", items.size() + " " + response.body().getResults());
                                        OnApiResult(1, "");
                                    } catch (Exception convert){
                                        convert.printStackTrace();
                                        OnApiResult( 2, "");
                                    }
                                    break;
                                case 403:
                                    OnApiResult(2, response.message());
                                    break;
                                default:
                                    OnApiResult(3, response.message());
                                    break;
                            }

                        }

                        @Override
                        public void onFailure(Call<ResultFilm> call, Throwable t) {
                            call.cancel();
                            OnApiResult(4, t.getMessage());
                        }
                    });
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public abstract void OnApiResult(int statusCode, String failReason);

    public List<Film> getItems() {
        return this.items;
    }
}
