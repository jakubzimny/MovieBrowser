package com.jzimny.moviebrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ResultListActivity extends AppCompatActivity {

   // public static final String API_KEY
    public final static String EXTRA_MOVIE_ID = "com.jzimny.moviebrowser.MOVIE_ID";
    private static final String TAG = "ResultListActivity";
    private ArrayList<Movie> movieList;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
        movieList = null;
        Intent intent = getIntent();
        String title = intent.getStringExtra(SearchBarActivity.EXTRA_TITLE);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;
        try {
            url = "https://api.themoviedb.org/3/search/movie?api_key="
                    + API_KEY + "&query=" + URLEncoder.encode(title,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Error occurred while encoding URL");
        }

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            handleResponse(response);
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

    private void handleResponse(JSONObject response) throws JSONException {
        movieList = new ArrayList<>();
        adapter = new MovieAdapter(this,R.layout.row, movieList);

        JSONArray results = response.getJSONArray("results");
        if(results.length()==0){
            TextView noResultsTV = findViewById(R.id.noResultsTV);
            noResultsTV.setText("No results found.");
            return;
        }
        for(int i=0; i<results.length();++i){
            JSONObject jObject = results.getJSONObject(i);
            movieList.add(new Movie(jObject));
        }

        ListView listView = findViewById(R.id.movieList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(ResultListActivity.this, MovieProfileActivity.class);
                intent.putExtra(EXTRA_MOVIE_ID,movieList.get(position).getId());
                startActivity(intent);
            }
        });
    }

}
