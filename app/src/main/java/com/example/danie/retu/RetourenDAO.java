package com.example.danie.retu;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RetourenDAO {

    @Insert
    void insert(RetourenEntity retourenEntity);

    @Query("DELETE FROM retouren")
    void deleteAll();

    @Query(("DELETE FROM retouren WHERE retoureID = :ID" ))
     void deleteById(String ID);

    @Update
    void update(RetourenEntity... retouren);

    @Query("SELECT * from retouren")
     List<RetourenEntity> getAllRetouren();

}
