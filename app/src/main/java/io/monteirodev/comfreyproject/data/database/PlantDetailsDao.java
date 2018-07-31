package io.monteirodev.comfreyproject.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.monteirodev.comfreyproject.data.PlantDetail;

@Dao
public abstract class PlantDetailsDao implements BaseDao<PlantDetail> {

    @Query("SELECT * FROM plantDetails where plantId = :plantId")
    public abstract LiveData<List<PlantDetail>> loadPlantDetails(int plantId);

    @Query("delete from plantDetails")
    public abstract void deleteAll();
}
