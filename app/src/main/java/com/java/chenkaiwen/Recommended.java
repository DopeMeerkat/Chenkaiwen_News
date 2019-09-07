package com.java.chenkaiwen;

import com.java.chenkaiwen.News.Keywords;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Recommended {
    HashMap<String, Integer> map;
    public Recommended() {map = new HashMap<String, Integer>();}

    public void insertKeys(List<Keywords> keywordsList) {
        for (int i = 0; i < keywordsList.size(); i ++)
        {
            int curr = 0;
            if (map.containsKey(keywordsList.get(i).word)) {
                try { curr++; } catch (Exception e){}
            }
            try {
                Integer tmp = new Integer(curr);
                map.put(keywordsList.get(i).word, tmp);
            } catch (Exception e){}
        }
    }

    public String getKey() {
        String mapKeys = "";
        int maxNum = 0;
        Iterator keys = map.keySet().iterator();
        //int i = 0;
        while(keys.hasNext())
        {
            //i ++;
            String key = (String)keys.next();
            if(map.get(key) != null && maxNum <= map.get(key).intValue()) {
                maxNum = map.get(key);
                mapKeys = key;
            }
        }
        return mapKeys;
    }

}
