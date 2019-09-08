package com.java.chenkaiwen;

import com.java.chenkaiwen.News.Keywords;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Recommended {
    private static Recommended INSTANCE = null;

    HashMap<String, Double> map;

    private Recommended() {map = new HashMap<String, Double>();}

    public static Recommended getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Recommended();
        }
        return(INSTANCE);
    }

    public void clear() {map.clear();}

    public void insertKeys(List<Keywords> keywordsList) {
        for (int i = 0; i < keywordsList.size(); i ++)
        {
            double curr = 0;
            if (map.containsKey(keywordsList.get(i).word)) {
                try {
                    curr = map.get(keywordsList.get(i).word);
                    curr += keywordsList.get(i).score;
                } catch (Exception e){}
            }
            try {
                Double tmp = new Double(curr);
                map.put(keywordsList.get(i).word, tmp);
            } catch (Exception e){}
        }
    }

    public String getKey() {
        String mapKey = "";
        double maxNum = 0;
        Iterator keys = map.keySet().iterator();
        while(keys.hasNext())
        {
            String key = (String)keys.next();
            if(map.get(key) != null && maxNum <= map.get(key).doubleValue()) {
                maxNum = map.get(key);
                mapKey = key;
            }
        }
        return mapKey;
    }

}
