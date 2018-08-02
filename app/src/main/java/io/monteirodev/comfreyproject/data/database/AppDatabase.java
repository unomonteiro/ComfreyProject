package io.monteirodev.comfreyproject.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import io.monteirodev.comfreyproject.data.AboutDetail;
import io.monteirodev.comfreyproject.data.GetInvolvedDetail;
import io.monteirodev.comfreyproject.data.Plant;
import io.monteirodev.comfreyproject.data.PlantDetail;
import io.monteirodev.comfreyproject.data.Recipe;
import io.monteirodev.comfreyproject.data.RecipeDetail;

// todo exportSchema once in final app
@Database(entities = {
        Plant.class, PlantDetail.class,
        Recipe.class, RecipeDetail.class,
        GetInvolvedDetail.class,
        AboutDetail.class},
        version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String COMFREY_DATABASE = "comfrey_database.db";
    private static AppDatabase sInstance;

    public abstract PlantsDao plantsDao();
    public abstract PlantDetailsDao plantDetailsDao();

    public abstract RecipesDao recipesDao();
    public abstract RecipeDetailsDao recipeDetailsDao();
    public abstract GetInvolvedDetailsDao getInvolvedDetailsDao();
    public abstract AboutDetailsDao aboutDetailsDao();

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room
                        .databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, COMFREY_DATABASE)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }
}
