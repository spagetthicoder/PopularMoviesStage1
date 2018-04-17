package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        TextView originalTitle = (TextView) findViewById(R.id.originalTitle);
        ImageView poster = (ImageView) findViewById(R.id.poster);
        TextView overview = (TextView) findViewById(R.id.overview);
        TextView voteAverage = (TextView) findViewById(R.id.voteAverage);
        TextView releaseDate = (TextView) findViewById(R.id.releaseDate);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(getString(R.string.movieParcel));
        originalTitle.setText(movie.getOriginalTitle());

        Picasso.with(this)
                .load(movie.getPosterPath())
                .resize(850, 850)
                .into(poster);

        overview.setText(movie.getOverview());
        voteAverage.setText("Vote Average Rating: " + movie.getVoteAverage());
        releaseDate.setText("Release date: " + movie.getReleaseDate());
    }
}
