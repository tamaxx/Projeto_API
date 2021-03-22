package com.example.projetoapi;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String MOVIES_URL = "https://www.omdbapi.com/?";
    private static final String QUERY_PARAMETER = "t";
    private static final String PLOT = "plot";
    private static final String API_KEY = "apikey";
        static String searchMovieInfo(String queryString){
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJSONString = null;
            try{
                Uri builtURI = Uri.parse(MOVIES_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAMETER, queryString)
                        .appendQueryParameter(PLOT, "full")
                        .appendQueryParameter(API_KEY, "310995c0")
                        .build();

                URL requestURL = new URL(builtURI.toString());

                urlConnection = (HttpURLConnection) requestURL.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder builder = new StringBuilder();
                String row;

                while((row = reader.readLine()) != null){
                    builder.append(row);
                    builder.append("\n");
                }
                if(builder.length() == 0){
                    return null;
                }
                movieJSONString = builder.toString();
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }
                if(reader !=  null) {
                    try {
                        reader.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            Log.d(LOG_TAG, movieJSONString);
            return movieJSONString;
        }
}
