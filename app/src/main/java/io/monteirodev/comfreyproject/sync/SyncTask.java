package io.monteirodev.comfreyproject.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import io.monteirodev.comfreyproject.api.ComfreyClient;
import io.monteirodev.comfreyproject.api.ComfreyInterface;
import io.monteirodev.comfreyproject.data.About;
import io.monteirodev.comfreyproject.data.AboutDetail;
import io.monteirodev.comfreyproject.data.ComfreyData;
import io.monteirodev.comfreyproject.data.GetInvolved;
import io.monteirodev.comfreyproject.data.GetInvolvedDetail;
import io.monteirodev.comfreyproject.data.Plant;
import io.monteirodev.comfreyproject.data.PlantDetail;
import io.monteirodev.comfreyproject.data.Recipe;
import io.monteirodev.comfreyproject.data.RecipeDetail;
import io.monteirodev.comfreyproject.data.database.AppDatabase;

public class SyncTask {
    private static final String TAG = SyncTask.class.getSimpleName();

    synchronized public static void syncData(@NonNull Context context) {
        try {
            ComfreyInterface comfreyInterface =
                    ComfreyClient.getClient().create(ComfreyInterface.class);

            ComfreyData comfreyData = comfreyInterface.getData().execute().body();
            if (comfreyData == null) {
                Log.d(TAG, "syncData: no comfreyData");
            } else {
                Log.d(TAG, "syncData: we have comfreyData!");
                AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());

                cleanOldData(db);

                insertPlants(db, comfreyData.getPlants());
                insertRecipes(db, comfreyData.getRecipes());
                insertGetInvolved(db, comfreyData.getGetInvolved());
                insertAbout(db, comfreyData.getAbout());
                Log.d(TAG, "syncData: comfreyData inserted in database");
            }
        } catch (Exception e) {
            Log.e(TAG, "syncData: error: " + e.getMessage(), e);
        }

        Log.d(TAG, "syncData: complete");

    }

    private static void cleanOldData(AppDatabase db) {
        db.plantsDao().deleteAll();
        db.plantDetailsDao().deleteAll();
        db.recipesDao().deleteAll();
        db.recipeDetailsDao().deleteAll();
        db.getInvolvedDetailsDao().deleteAll();
        db.aboutDetailsDao().deleteAll();
    }

    private static void insertPlants(AppDatabase db, List<Plant> plants) {
        if (!CollectionUtils.isEmpty(plants)) {
            db.plantsDao().insertList(plants);
            insertPlantDetails(db, plants);
        }
    }

    private static void insertPlantDetails(AppDatabase db, List<Plant> plants) {
        for (Plant plant : plants) {
            List<PlantDetail> plantDetails = plant.getDetails();
            if (!CollectionUtils.isEmpty(plantDetails)) {
                for (PlantDetail detail : plantDetails) {
                    detail.setPlantId(plant.getId());
                    db.plantDetailsDao().insert(detail);
                }
            }
        }
    }

    private static void insertRecipes(AppDatabase db, List<Recipe> recipes) {
        if (!CollectionUtils.isEmpty(recipes)) {
            db.recipesDao().insertList(recipes);
            insertRecipeDetails(db, recipes);
        }
    }

    private static void insertRecipeDetails(AppDatabase db, List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            List<RecipeDetail> recipeDetails = recipe.getDetails();
            if (!CollectionUtils.isEmpty(recipeDetails)) {
                for (RecipeDetail detail : recipeDetails) {
                    detail.setRecipeId(recipe.getId());
                    db.recipeDetailsDao().insert(detail);
                }
            }
        }
    }

    private static void insertGetInvolved(AppDatabase db, GetInvolved getInvolved) {
        if (getInvolved!= null) {
            ArrayList<GetInvolvedDetail> getInvolvedDetails = getInvolved.getDetails();
            if (!CollectionUtils.isEmpty(getInvolvedDetails)) {
                for (GetInvolvedDetail detail : getInvolvedDetails) {
                    db.getInvolvedDetailsDao().insert(detail);
                }
            }
        }
    }

    private static void insertAbout(AppDatabase db, About about) {
        if (about != null) {
            List<AboutDetail> aboutDetails = about.getDetails();
            if (!CollectionUtils.isEmpty(aboutDetails)) {
                for (AboutDetail detail : aboutDetails) {
                    db.aboutDetailsDao().insert(detail);
                }
            }
        }
    }
}
