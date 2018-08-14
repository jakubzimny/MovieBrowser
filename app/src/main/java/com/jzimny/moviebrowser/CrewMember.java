package com.jzimny.moviebrowser;

import org.json.JSONException;
import org.json.JSONObject;

public class CrewMember {
    private int id;
    private String job;
    private String name;
    private String profilePath;

    public CrewMember(JSONObject jObject) throws JSONException {
        this.id = jObject.getInt("id");
        this.job = jObject.getString("job");
        this.name = jObject.getString("name");
        this.profilePath = jObject.getString("profile_path");
    }

    public int getId() {
        return id;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
