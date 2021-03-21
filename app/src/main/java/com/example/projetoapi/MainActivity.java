package com.example.projetoapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    private Dialog dialog;
    private EditText txt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_search = findViewById(R.id.txt_search);

        dialog = new Dialog(this);

    }

    public void buscaFilme(View v){

        String mName = txt_search.getText().toString();
        if(mName.isEmpty()){
            txt_search.setError("Escreva um nome válido");
            return;
        }

        String url = "https://www.omdbapi.com/?t=" + mName +"&plot=full&apikey=310995c0";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                response -> {

                    try {

                        JSONObject movie = new JSONObject(response);

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
                },
                error -> {

                    Toast toast = Toast.makeText(getApplicationContext(), "Algo deu errado", Toast.LENGTH_SHORT);
                    toast.show();
                    Log.d("Error", Objects.requireNonNull(error.getMessage()));

                }
        );

        queue.add(stringRequest);
    }

}