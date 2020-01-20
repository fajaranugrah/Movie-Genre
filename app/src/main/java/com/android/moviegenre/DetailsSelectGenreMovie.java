package com.android.moviegenre;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.moviegenre.ListAdapter.FilmAdapter;
import com.android.moviegenre.Model.Film;
import com.android.moviegenre.Service.UpdateService;
import com.android.moviegenre.ViewModel.FilmViewModel;

import java.util.List;

public class DetailsSelectGenreMovie extends AppCompatActivity implements FilmViewModel.OnUpdateList {

    private FilmViewModel filmViewModel;
    private RecyclerView recyclerView;
    private FilmAdapter filmAdapter;
    IntentFilter intentFilter;
    UpdateService mService = null;
    boolean mBound = false;
    int page = 1;
    String idGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_filter_genre);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        filmAdapter = new FilmAdapter();
        recyclerView.setAdapter(filmAdapter);

        filmViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        filmViewModel.update = true;
        refreshList();

        if (getIntent().hasExtra("ID_GENRE")){
            idGenre = getIntent().getStringExtra("ID_GENRE");
        }

        startService();
    }

    // Monitors the state of the connection to the service.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            UpdateService.LocalBinder binder = (UpdateService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            mService.requestUpdate(DetailsSelectGenreMovie.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    @Override
    public void updateList() {
        refreshList();
    }

    @Override
    protected void onStart() {
        super.onStart();

        bindService(new Intent(DetailsSelectGenreMovie.this, UpdateService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onResume() {
        super.onResume();

        intentFilter = new IntentFilter();
        intentFilter.addAction("FILE_DOWNLOADED_UPDATE");

        registerReceiver(intentReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(intentReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    public void refreshList() {
        if (filmViewModel.update && filmViewModel.getAllFilms()!=null) {
            filmViewModel.getAllFilms().observe(this, new Observer<List<Film>>() {
                @Override
                public void onChanged(List<Film> films) {
                    filmViewModel.update = false;
                    //update RecyclerView
                    filmAdapter.setFilms(DetailsSelectGenreMovie.this, films);
                }
            });
            filmAdapter.setOnItemClickListener(new FilmAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(Film film) {
                    Intent intent = new Intent(DetailsSelectGenreMovie.this, DetailInformationMovie.class);
                    intent.putExtra("TITLE", film.getOriginal_title());
                    intent.putExtra("DESC", film.getOverview());
                    intent.putExtra("IMG", film.getPoster_path());
                    intent.putExtra("RATE", film.getVote_average());
                    startActivity(intent);
                }
            });
        } else {
            filmViewModel.GetFilm(DetailsSelectGenreMovie.this, "1", idGenre);
        }
    }

    public void startService() {
        startService(new Intent(DetailsSelectGenreMovie.this, UpdateService.class));
    }

    public void stopService() {
        stopService(new Intent(DetailsSelectGenreMovie.this, UpdateService.class));
    }

    BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("checkUpdate", "yeah Load update");
            page++;
            filmViewModel.GetFilm(DetailsSelectGenreMovie.this, String.valueOf(page), idGenre);
            mService.requestUpdate(DetailsSelectGenreMovie.this);
        }
    };
}
