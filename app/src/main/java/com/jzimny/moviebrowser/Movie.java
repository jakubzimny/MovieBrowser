package com.jzimny.moviebrowser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movie {

    private int voteCount;
    private int id;
    private int boxOffice;
    private int budget;
    private String runtime;
    private double voteAverage;
    private String title;
    private String posterPath;
    private String releaseYear;
    private String overview;
    private String originalTitle;
    private ArrayList<String> genres;
    private ArrayList<String> productionCountries;
    private ArrayList<CastMember> cast;
    private ArrayList<CrewMember> crew;

    public Movie(JSONObject jObject) throws JSONException {
        this.voteCount = jObject.getInt("vote_count");
        this.voteAverage = jObject.getDouble("vote_average");
        this.id = jObject.getInt("id");
        this.title = jObject.getString("title");
        this.originalTitle = jObject.getString("original_title");
        this.posterPath = jObject.getString("poster_path");

        if (jObject.getString("release_date").length() > 4) {
            this.releaseYear = jObject.getString("release_date").substring(0, 4);
        } else this.releaseYear = jObject.getString("release_date");

        this.overview = jObject.getString("overview");

        if (jObject.has("runtime")) this.runtime = jObject.getString("runtime");
        else this.runtime = "-";

        genres = new ArrayList<>();
        if (jObject.has("genres")) {
            if (jObject.getJSONArray("genres").length() != 0) {
                JSONArray jsonArray = jObject.getJSONArray("genres");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject temp = jsonArray.getJSONObject(i);
                    genres.add(temp.getString("name"));
                }
            } else genres.add("-");
        } else genres.add("-");

        productionCountries = new ArrayList<>();
        if (jObject.has("production_countries")) {
            if (jObject.getJSONArray("production_countries").length() != 0) {
                JSONArray jsonArray = jObject.getJSONArray("production_countries");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject temp = jsonArray.getJSONObject(i);
                    productionCountries.add(temp.getString("name"));
                }
            } else productionCountries.add("-");
        } else productionCountries.add("-");

        if (jObject.has("budget")) {
            this.budget = jObject.getInt("budget");
        } else this.budget = 0;

        if (jObject.has("revenue")) {
            this.boxOffice = jObject.getInt("revenue");
        } else this.boxOffice = 0;

        cast = new ArrayList<>();
        crew = new ArrayList<>();
        if (jObject.has("credits")) {
            JSONArray castArray = jObject.getJSONObject("credits").getJSONArray("cast");
            JSONArray crewArray = jObject.getJSONObject("credits").getJSONArray("crew");
            for(int i=0; i<castArray.length();i++)
                cast.add(new CastMember(castArray.getJSONObject(i)));
            for(int j=0; j<crewArray.length(); j++)
                crew.add(new CrewMember(crewArray.getJSONObject(j)));
        } else {
            cast = null;
            crew = null;
        }
    }

    @Override
    public String toString() {
        if (releaseYear.equals("")) return title;
        else return title + " (" + releaseYear + ") ";
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getId() {
        return id;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getOverview() {
        return overview;
    }

    public String getRuntime() {
        return runtime;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public ArrayList<String> getProductionCountries() {
        return productionCountries;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int getBoxOffice() {
        return boxOffice;
    }

    public int getBudget() {
        return budget;
    }

    public ArrayList<CastMember> getCast() {
        return cast;
    }

    public ArrayList<CrewMember> getCrew() {
        return crew;
    }
}
