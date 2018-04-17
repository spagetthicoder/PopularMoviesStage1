package com.example.android.popularmoviesstage1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hobbit2 on 31.3.2018 г..
 */

public class Movie implements Parcelable{

    private String mOrigTitle;
    private String mPosterPath;
    private String mReleaseDate;
    private String mOverview;
    private String mVoteAverage;

    final String MOVIEDB_POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";


    public Movie(){
    }

    public void setOriginalTitle(String originalTitle){
        mOrigTitle = originalTitle;
    }


    public void setPosterPath(String posterPath){
        mPosterPath = posterPath;
    }

    public void setReleaseDate(String releaseDate){
        mReleaseDate = releaseDate;
    }

    public void setOverview(String overview){
        mOverview = overview;
    }

    public void setVoteAverage(String voteAverage){
        mVoteAverage = voteAverage;
    }


    public String getOriginalTitle(){
        return mOrigTitle;
    }

    public String getPosterPath(){
        return MOVIEDB_POSTER_BASE_URL + mPosterPath;
    }

    public String getReleaseDate(){
        return mReleaseDate;
    }

    public String getOverview(){
        return mOverview;
    }

    public String getVoteAverage(){
        return mVoteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mOrigTitle);
        parcel.writeString(mPosterPath);
        parcel.writeString(mOverview);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mVoteAverage);

    }

    private Movie(Parcel in) {
        mOrigTitle = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mVoteAverage = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
