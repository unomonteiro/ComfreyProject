package io.monteirodev.comfreyproject.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

@Dao
public interface PlantsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlants(List<Plant> plants);

//    @Query("Drop Database comfrey_database.db")
//    public void nukeDatabase();
}
