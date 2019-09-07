package com.java.chenkaiwen;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.java.chenkaiwen.News.*;

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

    public String connect() {
        try {
            URL url = new URL(appendUri());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10_1000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            if (conn.getResponseCode() != 200) {
                Log.d("CONNECTION", "Error Connnection");
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            Log.d("CONNECTION", "Connection Successful");
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output = br.readLine();
            conn.disconnect();
            return output;
//            Log.d("CONNECTION", output);
//            JSONObject json = new JSONObject(output);
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            String prettyData = gson.toJson(json.getJSONArray("data"));
//            Log.d("CONNECTION", prettyData);
        } catch (URISyntaxException | IOException e) {
            Log.d("CONNECTION", e.toString());
            e.printStackTrace();
//        } catch (JSONException e) {
//            Log.d("CONNECTION", e.toString());
//            e.printStackTrace();
            return null;
        }
    }

    public String appendUri() throws URISyntaxException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").encodedAuthority("api2.newsminer.net").appendPath("svc").appendPath("news")
                .appendPath("queryNewsList");
        if (this.size != null)
            builder.appendQueryParameter("size", this.size);
        if (this.startDate != null)
            builder.appendQueryParameter("startDate", this.startDate);
        if (this.endDate != null)
            builder.appendQueryParameter("endDate", this.endDate);
        if (this.words != null)
            builder.appendQueryParameter("words", this.words);
        if (this.categories != null)
            builder.appendQueryParameter("categories", this.categories);
        String myUrl = builder.build().toString();
        Log.d("CONNECTION", myUrl);
        return myUrl;
    }

    public List<News> makeNewsList(String str) throws JSONException {
        Gson gson = new Gson();
        JSONObject js = new JSONObject(new String(str));
        JSONArray jsonArray = js.getJSONArray("data");
        List<News> newsList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i ++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            News news = jsonToNews(jsonObject);
            if(news != null) {
                newsList.add(news);
            }
        }
        Log.d("MakeList", "size = " + jsonArray.length());
        return newsList;
    }

    private News jsonToNews(JSONObject jsonObject) throws JSONException {
        final String newsID = jsonObject.getString("newsID");
        final String publishTime = jsonObject.getString("publishTime");
        String imgtxt = jsonObject.getString("image");
        final String category = jsonObject.getString("category");
        final String video = jsonObject.getString("video");
        final String title = jsonObject.getString("title");
        final String url = jsonObject.getString("url");
        final String content = jsonObject.getString("content");
        final String language = jsonObject.getString("language");
        int close = imgtxt.indexOf(",");
        if (close == -1){ close = imgtxt.indexOf("]");}
//        String image = "[";
//        image = image + imgtxt.substring(1, close);
//        image = image + "]";
        String image = "";
        if (!imgtxt.equals("[]"))
        {
            image = imgtxt.substring(1, close);
        }
//        Log.d("MakeNews", image);
        String trailText = "";
        int len = content.length();
        if (len > 100) len = 100;
//        Log.d("MakeNews", ""+len);
//        Log.d("MakeNews", arr[0]);
        for (int i = 0; i < len; i ++) {
            if(content.charAt(i) != '\t' && content.charAt(i) != '\n' && content.charAt(i) != ' ') {
                trailText = trailText + content.charAt(i);
            }
        }
        trailText = trailText + "...";
//        Log.d("MakeNews", trailText);
        JSONArray jsonKeywords = jsonObject.getJSONArray("keywords");
        List<Keywords> keywords = new ArrayList<>();
        for(int i = 0; i < jsonKeywords.length(); i ++)
        {
            JSONObject tmp = jsonKeywords.getJSONObject(i);
            Keywords keyword = new Keywords(tmp);
            keywords.add(keyword);
        }
        News news = new News(
                newsID, publishTime, image, category, video,
                title, url, content, trailText, language,false,false, keywords);
        //Log.d("News", newsID);
        return news;
    }
}