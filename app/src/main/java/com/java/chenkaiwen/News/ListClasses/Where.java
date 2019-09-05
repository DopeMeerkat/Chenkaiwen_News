package com.example.news_app.Database.News.ListClasses;

import org.json.JSONException;
import org.json.JSONObject;

//@Entity
public class Where {
    public double score;
    public String word;

    public Where(JSONObject o) throws JSONException {
        this.score = o.getInt("score");
        this.word = o.getString("word");
    }
}
