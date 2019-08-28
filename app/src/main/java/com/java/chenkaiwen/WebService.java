package com.java.chenkaiwen;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

public class WebService {
    private String size;
    private String startDate;
    private String endDate;
    private String words;
    private String categories;

    public void setSize(String size) {
        this.size = size;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public void setWords(String words) {
        this.words = words;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void connect() {
        try {
            URL url = new URL(appendUri());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10_1000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "utf-8");
//            Log.d("CONNECTION", "AfterThere");
            if (conn.getResponseCode() != 200) {
                Log.d("CONNECTION", "Error Connnection");
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            Log.d("CONNECTION", "Connection Successful");
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output = br.readLine();
            Log.d("CONNECTION", output);
            JSONObject json = new JSONObject(output);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyData = gson.toJson(json.getJSONArray("data"));
            Log.d("CONNECTION", prettyData);
            conn.disconnect();
        } catch (URISyntaxException | IOException e) {
            Log.d("CONNECTION", e.toString());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d("CONNECTION", e.toString());
            e.printStackTrace();
        }
    }

    public String appendUri() throws URISyntaxException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .encodedAuthority("api2.newsminer.net")
                .appendPath("svc")
                .appendPath("news")
                .appendPath("queryNewsList");
        if(this.size != null) builder.appendQueryParameter("size", this.size);
        if(this.startDate != null) builder.appendQueryParameter("startDate", this.startDate);
        if(this.endDate != null) builder.appendQueryParameter("endDate", this.endDate);
        if(this.words != null) builder.appendQueryParameter("words", this.words);
        if(this.categories != null) builder.appendQueryParameter("categories", this.categories);
        String myUrl = builder.build().toString();
        Log.d("CONNECTION", myUrl);
        return myUrl;
    }
}