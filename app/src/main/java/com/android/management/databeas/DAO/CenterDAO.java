package com.android.management.databeas.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.management.model.Center;

import java.util.List;

@Dao
public interface CenterDAO {

    @Insert()
    long insertCenter(Center model);

    @Update()
    int updateCenter(Center model);

    @Delete
    int deleteCenter(Center model);

    @Query("select name from center order by name asc ")
    List<String> getAllCenterName();

    @Query("select * from center order by name asc ")
    LiveData<List<Center>> getAllCenter();

}
