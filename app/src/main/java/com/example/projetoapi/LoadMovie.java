package com.example.projetoapi;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

public class LoadMovie extends AsyncTaskLoader<String> {
    private String mQueryString;
    LoadMovie(Context context, String queryString){
        super(context);
        mQueryString = queryString;
    }
    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.searchMovieInfo(mQueryString);
    }
}
