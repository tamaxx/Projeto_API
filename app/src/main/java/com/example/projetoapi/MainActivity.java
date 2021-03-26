package com.example.projetoapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
    private String _id;
    private String posterURL;
    private String title;
    private String year;
    private String genre;
    private String director;
    private String plot;
    private String cast;
    private JSONObject movie;
    private String resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_search = findViewById(R.id.txt_search);

        //this.deleteDatabase(BDHelper.BANCO);

        if(getSupportLoaderManager().getLoader(0) != null){
            getSupportLoaderManager().initLoader(0, null, this);
        }

    }

    public void RedirectDB(View v){
        Intent intentMovies = new Intent(this, DbActivity.class);
        startActivity(intentMovies);
    }

    public void searchMovie(View v) {

        dialog = new Dialog(this);
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
            movie = new JSONObject(data);
            String result = movie.getString("Response");

            if (result.equals("True")) {

                ImageView img_poster;
                TextView txt_title;
                TextView txt_year;
                TextView txt_genre;
                TextView txt_plot;
                TextView txt_director;
                TextView txt_cast;

                BDController crud = new BDController(getBaseContext());

                dialog.setContentView(R.layout.popup);
                img_poster = dialog.findViewById(R.id.img_poster);
                txt_title = dialog.findViewById(R.id.txt_title);
                txt_year = dialog.findViewById(R.id.txt_year);
                txt_genre = dialog.findViewById(R.id.txt_genre);
                txt_plot = dialog.findViewById(R.id.txt_plot);
                txt_director = dialog.findViewById(R.id.txt_director);
                txt_cast = dialog.findViewById(R.id.txt_cast);

                _id = movie.getString("imdbID");

                posterURL = movie.getString("Poster");

                Picasso.get().load(posterURL).into(img_poster);

                title = movie.getString("Title");
                txt_title.setText(title);

                year= "(" + movie.getString("Year") + ")";
                txt_year.setText(year);

                genre = movie.getString("Genre");
                txt_genre.setText(genre);

                director = "Directed by: " + movie.getString("Director");
                txt_director.setText(director);

                plot = movie.getString("Plot");
                txt_plot.setText(plot);

                cast = "Cast: " + movie.getString("Actors");
                txt_cast.setText(cast);

                resultado = crud.inserir(_id, title, posterURL, movie.getString("Year"), genre, movie.getString("Director"), movie.getString("Actors"), plot);

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
