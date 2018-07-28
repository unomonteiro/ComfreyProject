package io.monteirodev.comfreyproject.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.monteirodev.comfreyproject.data.Plant;

@Dao
public abstract class PlantsDao implements BaseDao<Plant> {

    @Query("SELECT * FROM plants")
    public abstract List<Plant> getPlants();

    @Query("SELECT * FROM plants ORDER BY name")
    public abstract LiveData<List<Plant>> loadPlants();

}
