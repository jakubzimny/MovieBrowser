package com.jzimny.moviebrowser;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MovieProfileActivity extends AppCompatActivity {

    //public static final String API_KEY =
    private static final String TAG = "MovieProfileActivity";
    private Movie movie;
    private TextView titleTV;
    private TextView descriptionTV;
    private TextView originalTitleTV;
    private TextView genresTV;
    private TextView countriesTV;
    private TextView budgetTV;
    private TextView boxOfficeTV;
    private TextView directorTV;
    private TextView screenplayTV;
    private TextView photoDirectorTV;
    private TextView musicTV;
    private ImageView poster;
    private TextView runtimeTV;
    private TextView ratingTV;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_profile);
        Intent intent = getIntent();
        int id = intent.getIntExtra(ResultListActivity.EXTRA_MOVIE_ID, 0);

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://api.themoviedb.org/3/movie/"
                + Integer.toString(id) + "?api_key=" + API_KEY + "&append_to_response=credits";
        movie = null;

        titleTV = findViewById(R.id.titleTV);
        descriptionTV = findViewById(R.id.descriptionTV);
       // castTV = findViewById(R.id.castTV);
        originalTitleTV = findViewById(R.id.originalTitleTV);
        genresTV = findViewById(R.id.genresTV);
        countriesTV = findViewById(R.id.countriesTV);
        budgetTV = findViewById(R.id.budgetTV);
        boxOfficeTV = findViewById(R.id.boxOfficeTV);
        directorTV = findViewById(R.id.directorTV);
        screenplayTV = findViewById(R.id.screenplayTV);
        photoDirectorTV = findViewById(R.id.photoDirectorTV);
        musicTV = findViewById(R.id.musicTV);
        runtimeTV = findViewById(R.id.runtimeTV);
        poster = findViewById(R.id.poster);
        context = this.getBaseContext();
        ratingTV = findViewById(R.id.ratingTV);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            movie = new Movie(response);
                            titleTV.setText(movie.getTitle() + " (" + movie.getReleaseYear() + ") ");
                            descriptionTV.setText(movie.getOverview());
                            //castTV.setText(getString(R.string.loremIpsum));
                            runtimeTV.setText("Runtime: "+movie.getRuntime()+" min.");
                            ratingTV.setText("Rating: "+Double.toString(movie.getVoteAverage())+"/10");

                            StringBuilder genresSB = new StringBuilder("");
                            for (String s : movie.getGenres()){
                                genresSB.append(s);
                                genresSB.append(", ");
                            }
                            genresTV.setText(genresSB.toString()
                                    .substring(0,genresSB.length()-2));

                            StringBuilder countriesSB = new StringBuilder("");
                            for (String s : movie.getProductionCountries()){
                                countriesSB.append(s);
                                countriesSB.append(", ");
                            }
                            countriesTV.setText(countriesSB.toString()
                                    .substring(0,countriesSB.length()-2));
                            originalTitleTV.setText(movie.getOriginalTitle());
                            if(movie.getBudget()!=0)
                                budgetTV.setText(Integer.toString(movie.getBudget())+"$");
                            else budgetTV.setText("-");
                            if(movie.getBoxOffice()!=0)
                                boxOfficeTV.setText(Integer.toString(movie.getBoxOffice())+"$");
                            else boxOfficeTV.setText("-");
                            if(movie.getCrew()!=null){
                                StringBuilder directorSB = new StringBuilder("");
                                StringBuilder screenplaySB = new StringBuilder("");
                                StringBuilder musicSB = new StringBuilder("");
                                StringBuilder photoSB = new StringBuilder("");
                                for(CrewMember crewMember : movie.getCrew()){
                                    if(crewMember.getJob().equals("Director")) {
                                        directorSB.append(crewMember.getName());
                                        directorSB.append(", ");
                                    }
                                    else if(crewMember.getJob().equals("Screenplay")) {
                                        screenplaySB.append(crewMember.getName());
                                        screenplaySB.append(", ");
                                    }
                                    else if(crewMember.getJob().equals("Original Music Composer")) {
                                        musicSB.append(crewMember.getName());
                                        musicSB.append(", ");
                                    }
                                    else if(crewMember.getJob().equals("Director of Photography")) {
                                        photoSB.append(crewMember.getName());
                                        photoSB.append(", ");
                                    }
                                }
                                if(directorSB.length()!=0)
                                    directorTV.setText(directorSB.toString()
                                        .substring(0,directorSB.length()-2));
                                else  directorTV.setText(getString(R.string.unknown));
                                if(screenplaySB.length()!=0)
                                    screenplayTV.setText(screenplaySB.toString()
                                        .substring(0,screenplaySB.length()-2));
                                else screenplayTV.setText(getString(R.string.unknown));
                                if(musicSB.length()!=0)
                                    musicTV.setText(musicSB.toString()
                                        .substring(0,musicSB.length()-2));
                                else musicTV.setText(getString(R.string.unknown));
                                if(photoSB.length()!=0)
                                    photoDirectorTV.setText(photoSB.toString()
                                            .substring(0,photoSB.length()-2));
                                else photoDirectorTV.setText(getString(R.string.unknown));
                            }
                            else {
                                directorTV.setText(getString(R.string.unknown));
                                screenplayTV.setText(getString(R.string.unknown));
                                musicTV.setText(getString(R.string.unknown));
                                photoDirectorTV.setText(getString(R.string.unknown));
                            }

                            if(movie.getPosterPath()!=null && movie.getPosterPath() != "")
                                Glide.with(context)
                                        .load("https://image.tmdb.org/t/p/w185/"+ movie.getPosterPath())
                                        .into(poster);
                            displayCast(movie.getCast());


                        } catch (JSONException e) {
                            Log.e(TAG, "Error occurred while parsing JSONObject");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error occurred while fetching response from TMDB API");
                        error.printStackTrace();
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void displayCast(ArrayList<CastMember> cast){
        if(cast !=null){
            TableLayout table = findViewById(R.id.castTable);
            for(CastMember actor : cast){

                TableLayout.LayoutParams par = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow
                        .LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,1);
                row.setGravity(Gravity.CENTER_VERTICAL);
                row.setLayoutParams(par);

                ImageView profile = new ImageView(this);
                TextView name = new TextView(this);
                name.setGravity(Gravity.CENTER);
                name.setPadding(20,0,0,0);
                TextView character = new TextView(this);
                character.setGravity(Gravity.CENTER);
                character.setPadding(20,0,0,0);
                TableRow.LayoutParams lpImage = new TableRow
                        .LayoutParams(154,231);
               // lpImage.gravity = Gravity.START;
                profile.setLayoutParams(lpImage);
                if(!actor.getProfilePath().equals("null"))
                    Glide.with(context)
                            .load("https://image.tmdb.org/t/p/w154/"+actor.getProfilePath())
                            .into(profile);

                name.setText(actor.getName());
                character.setText(actor.getCharacter());
                name.setLayoutParams(lp);
                character.setLayoutParams(lp);
                row.addView(profile);
                row.addView(name);
                row.addView(character);
                table.addView(row);
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2);
                View v = new View(this);
                v.setLayoutParams(params);

                v.setBackgroundResource(R.color.black);
                table.addView(v);

            }
        }
    }

}

