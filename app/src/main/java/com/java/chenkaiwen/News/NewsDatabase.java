package com.java.chenkaiwen.News;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {News.class}, version = 1)
@TypeConverters({NewsConverters.class})
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();

//    private static NewsDatabase INSTANCE;
//    private static final Object sLock = new Object();
//
//    public static NewsDatabase getInstance(Context context) {
//        synchronized (sLock) {
//            if (INSTANCE == null) {
//                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NewsDatabase.class, "news.db").build();
//            }
//        }
//        return INSTANCE;
//    }
}