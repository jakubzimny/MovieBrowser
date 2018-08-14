package com.jzimny.moviebrowser;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter {

    private ArrayList<Movie> movieList;
    private final Activity context;

    protected MovieAdapter(@NonNull Activity context, int resource, ArrayList<Movie> ml) {
        super(context, resource, ml);
        movieList = ml;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.row, null, true);

        Movie movie = movieList.get(position);

        TextView txtTitle = rowView.findViewById(R.id.txt);
        ImageView imageView = rowView.findViewById(R.id.img);
        if(movie.getPosterPath()!=null && movie.getPosterPath() != "")
            Glide.with(rowView)
                    .load(String.format("https://image.tmdb.org/t/p/w92/%s", movie.getPosterPath()))
                    .into(imageView);


        txtTitle.setText("  "+movie.toString());


        return rowView;
    }
}
