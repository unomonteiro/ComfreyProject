package io.monteirodev.comfreyproject.ui.recipes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.monteirodev.comfreyproject.data.RecipeDetail;
import io.monteirodev.comfreyproject.data.database.AppDatabase;

class RecipeDetailsViewModel extends ViewModel{
    private LiveData<List<RecipeDetail>> recipeDetails;

    RecipeDetailsViewModel(AppDatabase db, int recipeId) {
        recipeDetails = db.recipeDetailsDao().loadRecipeDetails(recipeId);
    }

    public LiveData<List<RecipeDetail>> getRecipeDetails() {
        return recipeDetails;
    }
}
