package io.monteirodev.comfreyproject.ui.plants;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.monteirodev.comfreyproject.data.PlantDetail;
import io.monteirodev.comfreyproject.data.Recipe;
import io.monteirodev.comfreyproject.data.database.AppDatabase;

public class PlantDetailsViewModel extends ViewModel {
    private LiveData<List<PlantDetail>> plantDetails;
    private LiveData<Recipe> recipe;

    PlantDetailsViewModel(AppDatabase db, int plantId) {
        plantDetails = db.plantDetailsDao().loadPlantDetails(plantId);
        recipe = db.recipesDao().loadRecipeByPlantId(plantId);
    }

    public LiveData<List<PlantDetail>> getPlantDetails() {
        return plantDetails;
    }

    public LiveData<Recipe> getRecipe() {
        return recipe;
    }
}
