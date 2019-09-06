package com.java.chenkaiwen.News;

import android.content.Context;
import androidx.room.Room;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {News.class}, version = 1)
@TypeConverters({NewsConverters.class})
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();

    private static volatile NewsDatabase INSTANCE;

    public static NewsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NewsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NewsDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}