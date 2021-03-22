package com.example.projetoapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private Dialog dialog;
    private EditText txt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_search = findViewById(R.id.txt_search);

        dialog = new Dialog(this);

        if(getSupportLoaderManager().getLoader(0) != null){
            getSupportLoaderManager().initLoader(0, null, this);
        }

    }

    public void searchMovie(View v) {

        String queryString = txt_search.getText().toString();

        InputMethodManager inputMethodManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        } else {
            if (queryString.length() == 0) {
                txt_search.setError("Escreva um nome válido");
                return;
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Verifique sua conexão", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if(args != null){
            queryString = args.getString("queryString");
        }
        return new LoadMovie(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

        try {
            JSONObject movie = new JSONObject(data);
            String result = movie.getString("Response");

            if (result.equals("True")) {

                ImageView img_poster;
                TextView txt_title;
                TextView txt_year;
                TextView txt_genre;
                TextView txt_plot;
                TextView txt_director;
                TextView txt_cast;

                dialog.setContentView(R.layout.popup);
                img_poster = dialog.findViewById(R.id.img_poster);
                txt_title = dialog.findViewById(R.id.txt_title);
                txt_year = dialog.findViewById(R.id.txt_year);
                txt_genre = dialog.findViewById(R.id.txt_genre);
                txt_plot = dialog.findViewById(R.id.txt_plot);
                txt_director = dialog.findViewById(R.id.txt_director);
                txt_cast = dialog.findViewById(R.id.txt_cast);

                String posterURL = movie.getString("Poster");

                Picasso.get().load(posterURL).into(img_poster);

                String title = movie.getString("Title");
                txt_title.setText(title);

                String year = "(" + movie.getString("Year") + ")";
                txt_year.setText(year);

                txt_genre.setText(movie.getString("Genre"));

                String director = "Directed by: " + movie.getString("Director");
                txt_director.setText(director);

                txt_plot.setText(movie.getString("Plot"));

                String cast = "Cast: " + movie.getString("Actors");
                txt_cast.setText(cast);

                dialog.show();

            } else {

                Toast toast = Toast.makeText(getApplicationContext(), "Filme não encontrado", Toast.LENGTH_SHORT);
                toast.show();

            }
        } catch (JSONException e) {

            e.printStackTrace();

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}