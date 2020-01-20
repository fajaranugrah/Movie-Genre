package com.android.moviegenre.Connection.ApiCallTask;

import android.app.Activity;

import com.android.moviegenre.Connection.APIClient;
import com.android.moviegenre.Connection.APIInterface;
import com.android.moviegenre.Model.Genre;
import com.android.moviegenre.Model.ResultGenre;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class GetGenre extends ApiCalling {

    Activity activity;
    ResultGenre resultGenre;
    ArrayList<Genre> items = new ArrayList<>();

    public GetGenre(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void callApi(String... params){

        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .doGetGenreList("f7b67d9afdb3c971d4419fa4cb667fbf")
                    .enqueue(new Callback<ResultGenre>() {
                        @Override
                        public void onResponse(Call<ResultGenre> call, Response<ResultGenre> response) {
                            switch (response.code()){
                                case 200:
                                    try {
                                        resultGenre = response.body();
                                        items = resultGenre.getGenres();
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
                        public void onFailure(Call<ResultGenre> call, Throwable t) {
                            call.cancel();
                            OnApiResult(4, t.getMessage());
                        }
                    });
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public abstract void OnApiResult(int statusCode, String failReason);

    public List<Genre> getItems() {
        return this.items;
    }
}