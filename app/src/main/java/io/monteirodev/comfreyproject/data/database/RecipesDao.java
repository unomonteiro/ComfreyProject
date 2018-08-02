package io.monteirodev.comfreyproject.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.monteirodev.comfreyproject.data.Recipe;

@Dao
public abstract class RecipesDao implements BaseDao<Recipe> {

    @Query("SELECT * FROM recipes")
    public abstract List<Recipe> getRecipes();

    @Query("SELECT * FROM recipes ORDER BY name")
    public abstract LiveData<List<Recipe>> loadRecipes();

    @Query("SELECT * FROM recipes where id = :id")
    public abstract LiveData<Recipe> loadRecipe(int id);

    @Query("delete from recipes")
    public abstract void deleteAll();
}
