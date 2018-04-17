package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnItemClickListener(movieClickListener);

        getMoviesMovieDB(getSortMethod());
    }

    private final GridView.OnItemClickListener movieClickListener = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Movie movie = (Movie) adapterView.getItemAtPosition(i);

            Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
            intent.putExtra(getResources().getString(R.string.movieParcel), movie);

            startActivity(intent);
        }
    };

    private String getSortMethod() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        return preferences.getString(getString(R.string.preferenceSortMethodKey), getString(R.string.movieSortPopularity));
    }

    private void getMoviesMovieDB(String sortMethod) {
        if (isInternetConnected()) {
            String apiKey = BuildConfig.API_KEY;

            OnTaskCompleted taskCompleted = new OnTaskCompleted() {
                @Override
                public void OnTaskCompleted(Movie[] movies) {
                    gridView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                }
            };

            FetchMovie fetchMovie = new FetchMovie(taskCompleted, apiKey);
            fetchMovie.execute(sortMethod);
        } else {
            Toast.makeText(this, "There is no internet connnection", Toast.LENGTH_LONG).show();
        }
    }


    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popularity:
                updateSharedPreferences(getString(R.string.movieSortPopularity));
                getMoviesMovieDB(getSortMethod());
                return true;
            case R.id.topRated:
                updateSharedPreferences(getString(R.string.movieSortTopRated));
                getMoviesMovieDB(getSortMethod());
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateSharedPreferences(String sortMethod) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.preferenceSortMethodKey), sortMethod);
        editor.apply();
    }
}
