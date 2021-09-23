package com.example.projetoapi;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class LoadNews extends AsyncTaskLoader<String> {
    private String mQueryString;
    LoadNews(Context context, String queryString){
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
        return NetworkUtils.searchNewMoviesInfo(mQueryString);
    }
}
