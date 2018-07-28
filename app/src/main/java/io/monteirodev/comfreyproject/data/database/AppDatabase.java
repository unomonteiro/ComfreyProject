package io.monteirodev.comfreyproject.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import io.monteirodev.comfreyproject.data.Plant;

// todo exportSchema once in final app
@Database(entities = {Plant.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String COMFREY_DATABASE = "comfrey_database.db";
    private static AppDatabase sInstance;

    public abstract PlantsDao plantsDao();

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room
                        .databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, COMFREY_DATABASE)
                        .build();
            }
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }
}
