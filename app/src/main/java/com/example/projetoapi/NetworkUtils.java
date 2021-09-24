
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

    private static final String NEWMOVIES_URL = "http://192.168.0.191:45455/movieByPlatform?";
    private static final String NEWMOVIES_QUERYPARAMETER = "namePlatform";

    private static final String DIRECTORS_URL = "http://192.168.0.191:45455/directorByMovie?";
    private static final String DIRECTOR_QUERYPARAMETER = "nameMovie";

    static String searchMovieInfo(String queryString){
        try{
            Uri builtURI = Uri.parse(MOVIES_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAMETER, queryString)
                    .appendQueryParameter(PLOT, "full")
                    .appendQueryParameter(API_KEY, "310995c0")
                    .build();
            return request(new URL(builtURI.toString()));
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    static String searchNewMoviesInfo(String queryString){
        try{
            Uri builtURI = Uri.parse(NEWMOVIES_URL).buildUpon()
                    .appendQueryParameter(NEWMOVIES_QUERYPARAMETER, queryString)
                    .build();
            return request(new URL (builtURI.toString()));
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    static String searchDirectorsInfo(String queryString){
        try{
            Uri builtURI = Uri.parse(DIRECTORS_URL).buildUpon()
                    .appendQueryParameter(DIRECTOR_QUERYPARAMETER, queryString)
                    .build();
            return request(new URL (builtURI.toString()));
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    static private String request(URL url){
        return request(url, "GET");
    }

    static private String request(URL url, String method){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JSONString = null;
        try{
            URL requestURL = url;

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.addRequestProperty("Content-Type", "application/json");
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
            JSONString = builder.toString();
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
        Log.d(LOG_TAG, JSONString);
        return JSONString;
    }
}
