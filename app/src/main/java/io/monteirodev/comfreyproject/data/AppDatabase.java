package io.monteirodev.comfreyproject.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

// todo exportSchema once in final app
@Database(entities = {Plant.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String COMFREY_DATABASE = "comfrey_database.db";
    private static AppDatabase INSTANCE;

    public abstract PlantsDao plantsDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = Room
                        .databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, COMFREY_DATABASE)
                        .build();
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
