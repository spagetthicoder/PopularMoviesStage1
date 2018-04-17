package com.example.android.popularmoviesstage1;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by hobbit2 on 27.3.2018 г..
 */

public class FetchMovie extends AsyncTask<String, Void, Movie[]> {

    private static final String LOG_TAG = FetchMovie.class.getSimpleName();
    private final String mApiKey;
    private final OnTaskCompleted mListerner;

    public FetchMovie(OnTaskCompleted listener, String apiKey) {
        super();

        mListerner = listener;
        mApiKey = apiKey;
    }

    @Override
    protected Movie[] doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;

        String movieJsonString = null;

        try {
            URL url = getApiUrl(params);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();

            if (inputStream == null) {
                return null;
            }
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            movieJsonString = builder.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error: ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream: ", e);
                }
            }
        }

        try {
            return getMoviesJson(movieJsonString);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    private Movie[] getMoviesJson(String movieJsonString) throws JSONException {
        JSONObject moviesJSON = new JSONObject(movieJsonString);
        JSONArray results = moviesJSON.getJSONArray("results");

        Movie[] movies = new Movie[results.length()];

        for (int i = 0; i < results.length(); i++) {

            movies[i] = new Movie();

            JSONObject movie = results.getJSONObject(i);

            movies[i].setOriginalTitle(movie.getString("original_title"));
            movies[i].setPosterPath(movie.getString("poster_path"));
            movies[i].setReleaseDate(movie.getString("release_date"));
            movies[i].setOverview(movie.getString("overview"));
            movies[i].setVoteAverage(movie.getString("vote_average"));

        }

        return movies;
    }

    private URL getApiUrl(String[] params) throws MalformedURLException {
        final Uri builtUri;
        final String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/movie/popular?";
        final String MOVIEDB2_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated?";
        final String API_KEY = "api_key";

        if (params[0].equals("popularity")) {
            builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY, mApiKey)
                    .build();

        } else {
            builtUri = Uri.parse(MOVIEDB2_BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY, mApiKey)
                    .build();
        }
        return new URL(builtUri.toString());
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        super.onPostExecute(movies);

        mListerner.OnTaskCompleted(movies);

    }
}
