package com.example.projetoapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.Manifest;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.projetoapi.views.CustomView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.projetoapi.ProfileActivity.EXTRA_NUMBER;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>,
        NavigationView.OnNavigationItemSelectedListener, OnSuccessListener<Location>, OnFailureListener, SensorEventListener {
    public static final String EXTRA_TEXT = "com.example.projetoapi.EXTRA_TEXT";
    public final static int LOCATION_CODE = 1;

    float[] results = new float[1];

    double latitude1, longitude1, latitude2, longitude2;

    private CustomView customView;
    private Dialog dialogInfo;
    private TextView txt_info;

    private TextView name_profile;
    private CircleImageView img_profile;
    private DrawerLayout drawerLayout;

    private Dialog dialogMovie;
    private EditText txt_search;

    private String _id;
    private String posterURL;
    private String title;
    private String year;
    private String year_exhib;
    private String genre;
    private String director;
    private String director_exhib;
    private String plot;
    private String cast;
    private String cast_exhib;
    private JSONObject movie;
    private ToggleButton btn_fav;

    private String resultado;
    private int currentID;

    private SensorManager sensorManager;
    private Sensor sensor;


    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        customView = findViewById(R.id.customView_about);

        dialogInfo = new Dialog(this);
        dialogInfo.setContentView(R.layout.info_popup);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_navigation, R.string.close_navigation);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));
        toggle.syncState();

        txt_search = findViewById(R.id.txt_search);

        //this.deleteDatabase(BDHelper.BANCO);

        if(getSupportLoaderManager().getLoader(0) != null){
            getSupportLoaderManager().initLoader(0, null, this);
        }

        name_profile = navigationView.getHeaderView(0).findViewById(R.id.txt_user);
        img_profile = navigationView.getHeaderView(0).findViewById(R.id.img_user);
        img_profile.setTag(R.drawable.clockworkorange);

        Intent intentFromProfile = getIntent();
        int pictureFromProfile = intentFromProfile.getIntExtra(EXTRA_NUMBER, 0);
        String nameFromProfile = intentFromProfile.getStringExtra(EXTRA_TEXT);

        SharedPreferences info = getSharedPreferences("Info", MODE_PRIVATE);

        if(pictureFromProfile != 0){
            img_profile.setImageResource(pictureFromProfile);
            img_profile.setTag(pictureFromProfile);
            info.edit().putInt("Image", pictureFromProfile).apply();
        } else {
            int image = info.getInt("Image", 0);
            if(image != 0) {
                img_profile.setImageResource(image);
                img_profile.setTag(image);
            }
        }
        if(nameFromProfile != null){
            name_profile.setText(nameFromProfile);
            info.edit().putString("Name", nameFromProfile).apply();
        } else {
            String name = info.getString("Name", "User");
            if(name != null){
                name_profile.setText(name);
            }
        }
    }

    @Override

    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override

    public void onSensorChanged(SensorEvent event){

        float light = event.values[0];
        if (light < 10) {
            openLightDialog();
        }
    }

    private void openLightDialog(){
        LightDialog lightDialog = new LightDialog();
        lightDialog.show(getSupportFragmentManager(), "lightDialog");
    }

    @Override

    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }


    public void infoPopUp(View v){
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CODE);
            Toast toast = Toast.makeText(this, "A permissão não foi concedida", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            final FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this);
            fusedLocationProviderClient.getLastLocation().addOnFailureListener(this);
        }
        dialogInfo.show();
    }

    @Override
    public void onFailure(@NonNull Exception e){Log.e("Location not detected", "errors", e);}

    @Override
    public void onSuccess(Location location){
        if(location != null){
            latitude1 = location.getLatitude();
            longitude1 = location.getLongitude();
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            try{
                List<Address> locales = geocoder.getFromLocation(34.134117, -118.321495, 1);
                if(locales.size() == 0){
                    return;
                }
                Address local = locales.get(0);
                latitude2 =  local.getLatitude();
                longitude2 = local.getLongitude();
                Location.distanceBetween(latitude1, longitude1, latitude2, longitude2, results);
                float results2 = results[0] / 1000;

                txt_info = dialogInfo.findViewById(R.id.txt_info);
                txt_info.setText(String.valueOf(getString(R.string.info_text1) + " " + results2 + " km " + getString(R.string.info_text2)));
            } catch (Exception e){
                Log.e("Exception", "errors", e);
            }

        }
        else {
            txt_info = dialogInfo.findViewById(R.id.txt_info);
            txt_info.setText(String.valueOf(getString(R.string.info_text1) + " Too far " + getString(R.string.info_text2)));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        TextView txt_username = findViewById(R.id.txt_user);

        String username = txt_username.getText().toString();
        int pictureToProfile = (int) img_profile.getTag();

        switch (item.getItemId()){
            case R.id.nav_profile:
                    Intent intentToProfile = new Intent(this, ProfileActivity.class);
                    intentToProfile.putExtra(EXTRA_TEXT, username);
                    intentToProfile.putExtra(EXTRA_NUMBER, pictureToProfile);
                    startActivity(intentToProfile);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void RedirectDB(View v){
        Intent intentMovies = new Intent(this, DbActivity.class);
        startActivity(intentMovies);
    }

    public void searchMovie(View v) {

        dialogMovie = new Dialog(this);
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

    @Override
    public  void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

                dialogMovie.setContentView(R.layout.popup);
                img_poster = dialogMovie.findViewById(R.id.img_poster);
                txt_title = dialogMovie.findViewById(R.id.txt_title);
                txt_year = dialogMovie.findViewById(R.id.txt_year);
                txt_genre = dialogMovie.findViewById(R.id.txt_genre);
                txt_plot = dialogMovie.findViewById(R.id.txt_plot);
                txt_director = dialogMovie.findViewById(R.id.txt_director);
                txt_cast = dialogMovie.findViewById(R.id.txt_cast);
                btn_fav = dialogMovie.findViewById(R.id.btn_fav);

                BDController crud = new BDController(getBaseContext());

                _id = movie.getString("imdbID");

                posterURL = movie.getString("Poster");

                Picasso.get().load(posterURL).into(img_poster);

                title = movie.getString("Title");
                txt_title.setText(title);

                year = movie.getString("Year");
                year_exhib = "(" + movie.getString("Year") + ")";
                txt_year.setText(year_exhib);

                genre = movie.getString("Genre");
                txt_genre.setText(genre);

                director = movie.getString("Director");
                director_exhib = "Directed by: " + movie.getString("Director");
                txt_director.setText(director_exhib);

                plot = movie.getString("Plot");
                txt_plot.setText(plot);

                cast = movie.getString("Actors");
                cast_exhib = "Cast: " + movie.getString("Actors");
                txt_cast.setText(cast_exhib);

                dialogMovie.show();

                if(crud.procuraID(_id)){
                    btn_fav.setChecked(true);
                }
                else{
                    btn_fav.setChecked(false);
                }

                btn_fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            resultado = crud.inserir(_id, title, posterURL, year, genre, director, cast, plot);
                        }
                        else{
                            crud.deletar(_id);
                        }
                    }
                });

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
