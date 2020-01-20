package com.android.moviegenre;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class DetailInformationMovie extends AppCompatActivity {

    ImageView view_img;
    TextView title;
    TextView description;
    TextView rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_movie);

        view_img = (ImageView) findViewById(R.id.view_img);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        rate = (TextView) findViewById(R.id.rate);

        if (getIntent().hasExtra("IMG")){
            Glide.with(DetailInformationMovie.this)
                    .load("https://image.tmdb.org/t/p/w300" + getIntent().getStringExtra("IMG"))
                    .transition(withCrossFade())
                    .into(view_img);
        }

        if (getIntent().hasExtra("TITLE")){
            title.setText(getIntent().getStringExtra("TITLE"));
        }

        if (getIntent().hasExtra("DESC")){
            description.setText(getIntent().getStringExtra("DESC"));
        }

        if (getIntent().hasExtra("RATE")){
            rate.setText(getIntent().getStringExtra("RATE"));
        }
    }

}
