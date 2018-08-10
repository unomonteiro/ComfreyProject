package io.monteirodev.comfreyproject.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.monteirodev.comfreyproject.data.Plant;

@Dao
public abstract class PlantsDao implements BaseDao<Plant> {

    @Query("SELECT count(1) FROM plants")
    public abstract int countPlants();

    @Query("SELECT * FROM plants ORDER BY name")
    public abstract LiveData<List<Plant>> loadPlants();

    @Query("SELECT * FROM plants WHERE id = :id")
    public abstract LiveData<Plant> loadPlant(int id);

    // https://stackoverflow.com/a/48406229/1329854
    @Query("SELECT * FROM plants WHERE id IN (:ids) ORDER BY name")
    public abstract List<Plant> getPlantsSync(List<Integer> ids);

    @Query("DELETE FROM plants")
    public abstract void deleteAll();
}
