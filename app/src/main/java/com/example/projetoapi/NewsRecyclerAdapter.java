package com.example.projetoapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {
    private ArrayList<NewMovie> newMoviesList;

    public NewsRecyclerAdapter(ArrayList<NewMovie> newMoviesList){
        this.newMoviesList = newMoviesList;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_newTitle;
        private ImageView img_newPoster;

        public NewsViewHolder(final View view){
            super(view);
            txt_newTitle = view.findViewById(R.id.txt_newTitle);
            img_newPoster = view.findViewById(R.id.img_newPoster);
        }
    }

    @NonNull
    @Override
    public NewsRecyclerAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.newslist_item, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        String title = newMoviesList.get(position).getNewTitle();

        holder.txt_newTitle.setText(title);

        String poster = newMoviesList.get(position).getNewPoster();
        Picasso.get().load(poster).into(holder.img_newPoster);
    }

    @Override
    public int getItemCount() {
        return newMoviesList.size();
    }
}
