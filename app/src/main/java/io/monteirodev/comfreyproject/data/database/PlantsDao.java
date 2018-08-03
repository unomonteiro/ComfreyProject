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

    @Query("SELECT * FROM plants where id = :id")
    public abstract LiveData<Plant> loadPlant(int id);

    @Query("delete from plants")
    public abstract void deleteAll();
}
