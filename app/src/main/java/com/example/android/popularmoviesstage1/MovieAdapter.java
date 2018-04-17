package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by hobbit2 on 2.4.2018 г..
 */

public class MovieAdapter extends BaseAdapter{


    private final Context mContext;
    private final Movie[] mMovie;

    MovieAdapter(Context context, Movie[] movie){
        mContext = context;
        mMovie = movie;
    }

    @Override
    public int getCount() {
        if(mMovie == null){
            return 0;
        }
        return mMovie.length;
    }

    @Override
    public Object getItem(int i) {
        if(mMovie.length == 0 || mMovie == null){
            return -1;
        }
        return mMovie[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;

        if(view==null){
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) view;
        }

        Picasso.with(mContext)
                .load(mMovie[i].getPosterPath())
                .resize(185, 185)
                .into(imageView);

        return imageView;
    }
}
