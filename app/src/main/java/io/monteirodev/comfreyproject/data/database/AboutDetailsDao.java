package io.monteirodev.comfreyproject.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.monteirodev.comfreyproject.data.AboutDetail;

@Dao
public abstract class AboutDetailsDao implements BaseDao<AboutDetail> {

    @Query("SELECT * FROM aboutDetails")
    public abstract LiveData<List<AboutDetail>> loadAboutDetails();

    @Query("delete from aboutDetails")
    public abstract void deleteAll();
}
