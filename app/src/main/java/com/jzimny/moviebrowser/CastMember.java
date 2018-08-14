package com.jzimny.moviebrowser;

import org.json.JSONException;
import org.json.JSONObject;

public class CastMember {
    //private int castId;
    private String character;
    private int id;
    private String name;
    private String profilePath;

    public CastMember(JSONObject jObject) throws JSONException {
        this.id = jObject.getInt("id");
        this.character = jObject.getString("character");
        this.name = jObject.getString("name");
        this.profilePath = jObject.getString("profile_path");
    }

    public String getCharacter() {
        return character;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
