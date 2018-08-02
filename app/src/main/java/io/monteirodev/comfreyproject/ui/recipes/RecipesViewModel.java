package io.monteirodev.comfreyproject.ui.recipes;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.monteirodev.comfreyproject.data.Recipe;
import io.monteirodev.comfreyproject.data.database.AppDatabase;

public class RecipesViewModel extends AndroidViewModel {

    private LiveData<List<Recipe>> recipes;

    public RecipesViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(this.getApplication());
        recipes = db.recipesDao().loadRecipes();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}
