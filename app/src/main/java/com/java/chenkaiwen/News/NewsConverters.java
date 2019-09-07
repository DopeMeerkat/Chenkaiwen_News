package com.java.chenkaiwen.News;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class NewsConverters {
    @TypeConverter
    public List<Keywords> stringToKeywordsList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type keywords = new TypeToken<List<Keywords>>(){}.getType();
        return new Gson().fromJson(data, keywords);
    }

    @TypeConverter
    public String keywordsListToString(List<Keywords> keywords) {
        Gson gson = new Gson();
        return new Gson().toJson(keywords);
    }
}