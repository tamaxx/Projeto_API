package com.example.projetoapi;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class LoadNews extends AsyncTaskLoader<ArrayList<String>> {
    private String mQueryStringPlatform;
    private ArrayList<String> queryResults;
    LoadNews(Context context, String queryStringPlatform){
        super(context);
        mQueryStringPlatform = queryStringPlatform;
    }

    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<String> loadInBackground() {
        queryResults = new ArrayList<>();
        queryResults.add(NetworkUtils.searchNewMoviesInfo(mQueryStringPlatform));
        return queryResults;
    }
}
