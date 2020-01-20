package com.android.moviegenre.ListAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.moviegenre.Model.Film;
import com.android.moviegenre.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.NoteHolder> {

    private Activity activity;
    private List<Film> films = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film_item, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Film film = films.get(position);
        holder.title.setText(film.getOriginal_title());
        holder.time.setText(film.getRelease_date());
        Glide.with(activity)
                .load("https://image.tmdb.org/t/p/w300" + film.getBackdrop_path())
                .transition(withCrossFade())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public void setFilms(Activity activity, List<Film> films){
        this.activity = activity;
        this.films = films;
        notifyDataSetChanged();
    }

    public Film getFilmAt(int position) {
        return films.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView time;
        private ImageView imageView;

        public NoteHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            time = (TextView) view.findViewById(R.id.time);
            imageView = (ImageView) view.findViewById(R.id.img_movie);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener!=null && position!=RecyclerView.NO_POSITION){
                        onItemClickListener.OnItemClick(films.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(Film film);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
