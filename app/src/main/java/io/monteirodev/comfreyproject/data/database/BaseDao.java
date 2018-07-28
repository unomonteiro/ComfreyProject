package io.monteirodev.comfreyproject.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T obj);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<T> obj);

    @Update
    void update(T obj);

    @Delete
    void delete(T obj);
}