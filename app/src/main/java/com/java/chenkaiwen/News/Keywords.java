package com.java.chenkaiwen.News;

import org.json.JSONException;
import org.json.JSONObject;

public class Keywords {
    public double score;
    public String word;

    public Keywords(JSONObject o) throws JSONException {
        this.score = o.getInt("score");
        this.word = o.getString("word");
    }
}