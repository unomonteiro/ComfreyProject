package io.monteirodev.comfreyproject.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.monteirodev.comfreyproject.data.GetInvolvedDetail;

@Dao
public abstract class GetInvolvedDetailsDao implements BaseDao<GetInvolvedDetail> {

    @Query("SELECT * FROM getInvolvedDetails")
    public abstract LiveData<List<GetInvolvedDetail>> loadGetInvolvedDetails();

    @Query("delete from getInvolvedDetails")
    public abstract void deleteAll();
}
