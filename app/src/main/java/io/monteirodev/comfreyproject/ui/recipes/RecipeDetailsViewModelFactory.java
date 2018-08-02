package io.monteirodev.comfreyproject.ui.recipes;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import io.monteirodev.comfreyproject.data.database.AppDatabase;

class RecipeDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mRecipeId;

    RecipeDetailsViewModelFactory(AppDatabase db, int recipeId) {
        mDb = db;
        mRecipeId = recipeId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeDetailsViewModel(mDb, mRecipeId);
    }
}
