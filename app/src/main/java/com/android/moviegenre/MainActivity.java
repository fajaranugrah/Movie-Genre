package com.android.moviegenre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.moviegenre.ListAdapter.GenreAdapter;
import com.android.moviegenre.Model.Genre;
import com.android.moviegenre.ViewModel.FilmViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FilmViewModel filmViewModel;
    private RecyclerView recyclerView;
    private GenreAdapter genreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        genreAdapter = new GenreAdapter();
        recyclerView.setAdapter(genreAdapter);

        filmViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        refreshList();
    }

    public void refreshList() {
        if (filmViewModel.getAllGenres()!=null){
            filmViewModel.getAllGenres().observe(this, new Observer<List<Genre>>() {
                @Override
                public void onChanged(List<Genre> genres) {
                    //update RecyclerView
                    genreAdapter.setGenre(genres);
                }
            });

            genreAdapter.setOnItemClickListener(new GenreAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(Genre genre) {
                    Intent intent = new Intent(MainActivity.this, DetailsSelectGenreMovie.class);
                    intent.putExtra("ID_GENRE", String.valueOf(genre.getId()));
                    startActivity(intent);
                }
            });
        } else {
            filmViewModel.GetGenre(MainActivity.this);
        }
    }
}
