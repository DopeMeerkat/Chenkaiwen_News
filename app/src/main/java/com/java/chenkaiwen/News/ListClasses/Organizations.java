package com.example.news_app.Database.News.ListClasses;


import org.json.JSONException;
import org.json.JSONObject;

//@Entity
public class Organizations {
    public int count;
    public String linkedURL;
    public String mention;

    public Organizations(JSONObject o) throws JSONException {
        this.count = o.getInt("count");
        this.linkedURL = o.getString("linkedURL");
        this.mention = o.getString("mention");
    }
}
