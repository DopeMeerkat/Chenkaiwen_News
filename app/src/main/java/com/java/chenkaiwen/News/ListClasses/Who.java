package com.example.news_app.Database.News.ListClasses;

import org.json.JSONException;
import org.json.JSONObject;

//@Entity
public class Who {
    public double score;
    public String word;

    public Who(JSONObject o) throws JSONException {
        this.score = o.getInt("score");
        this.word = o.getString("word");
    }
}
