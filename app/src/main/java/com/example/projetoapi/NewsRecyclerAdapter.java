package com.example.projetoapi;

import android.text.method.ScrollingMovementMethod;
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
        private TextView txt_newYear;
        private ImageView img_newPoster;

        public NewsViewHolder(final View view){
            super(view);
            txt_newTitle = view.findViewById(R.id.txt_newTitle);
            txt_newYear = view.findViewById(R.id.txt_newYear);
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
        //int year = newMoviesList.get(position).getNewYear();

        holder.txt_newTitle.setText(title);
        //holder.txt_newYear.setText(year);

        String poster = newMoviesList.get(position).getNewPoster();
        Picasso.get().load(poster).into(holder.img_newPoster);
    }

    @Override
    public int getItemCount() {
        return newMoviesList.size();
    }
}
