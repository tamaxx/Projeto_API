package com.example.projetoapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, AdapterView.OnItemSelectedListener {

    private ArrayList<NewMovie> newMoviesList;
    private JSONArray newMovies;
    private JSONArray newDirectors;
    private JSONObject newMovie;
    private RecyclerView recyclerView;
    private Spinner platformSpinner;
    private String queryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        if(getSupportLoaderManager().getLoader(0) != null){
            getSupportLoaderManager().initLoader(0, null, this);
        }

        recyclerView = findViewById(R.id.recyclerView_news);
        platformSpinner = findViewById(R.id.spn_platforms);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.platforms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        platformSpinner.setAdapter(adapter);
        platformSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        queryString = parent.getItemAtPosition(position).toString();
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
                Toast toast = Toast.makeText(getApplicationContext(), "Erro inesperado na busca", Toast.LENGTH_SHORT);
                toast.show();
                return;
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Verifique sua conexão", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";

        if(args != null){
            queryString = args.getString("queryString");
        }
        return new LoadNews(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            newMovies = new JSONArray(data);
            newMoviesList = new ArrayList<>();
            for(int i = 0; i < newMovies.length(); i++) {
                newMovie = newMovies.getJSONObject(i);
                newMoviesList.add(new NewMovie(
                        newMovie.getInt("ID_F"),
                        newMovie.getString("Name"),
                        newMovie.getString("Poster"),
                        newMovie.getString("Plot"),
                        newMovie.getString("Cast"),
                        newMovie.getInt("Year"),
                        newMovie.getString("Producer"),
                        newMovie.getString("Duration"),
                        newMovie.getInt("ID_D")
                ));
                setAdapter();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setAdapter(){
        NewsRecyclerAdapter adapter = new NewsRecyclerAdapter(newMoviesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}