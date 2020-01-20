package com.android.moviegenre.ListAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.moviegenre.Model.Genre;
import com.android.moviegenre.R;

import java.util.ArrayList;
import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.NoteHolder> {

    private List<Genre> genres = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genre_item, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Genre genre = genres.get(position);
        holder.title.setText(genre.getName());
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public void setGenre(List<Genre> genre){
        this.genres = genre;
        notifyDataSetChanged();
    }

    public Genre getGenreAt(int position) {
        return genres.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public NoteHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener!=null && position!=RecyclerView.NO_POSITION){
                        onItemClickListener.OnItemClick(genres.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(Genre genre);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
