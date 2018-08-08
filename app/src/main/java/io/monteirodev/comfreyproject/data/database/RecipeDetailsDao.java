package io.monteirodev.comfreyproject.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.monteirodev.comfreyproject.data.RecipeDetail;

@Dao
public abstract class RecipeDetailsDao implements BaseDao<RecipeDetail> {

    @Query("SELECT count(1) FROM recipeDetails")
    public abstract int countRecipeDetails();

    @Query("SELECT * FROM recipeDetails where recipeId = :recipeId")
    public abstract LiveData<List<RecipeDetail>> loadRecipeDetails(int recipeId);

    @Query("delete from recipeDetails")
    public abstract void deleteAll();
}
