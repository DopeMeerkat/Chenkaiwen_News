package com.example.news_app.Database.News.ListClasses;

import org.json.JSONException;
import org.json.JSONObject;

public class Locations {
    public int count;
    public String linkedURL;
    public String mention;

    public Locations(JSONObject o) throws JSONException {
        this.count = o.getInt("count");
        this.linkedURL = o.getString("linkedURL");
        this.mention = o.getString("mention");
    }
}
