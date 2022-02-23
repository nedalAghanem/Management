package com.android.management.databeas.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.management.model.Branch;
import com.android.management.model.Episodes;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface EpisodesDAO {

    @Insert()
    void insertEpisodes(ArrayList<Episodes> model);

    @Insert()
    long insertEpisodes(Episodes model);

    @Update()
    int updateEpisodes(Episodes model);

    @Delete
    int deleteEpisodes(Episodes model);

    @Query("select name from Episodes order by name asc ")
    List<String> getAllEpisodesName();

    @Query("select * from Episodes order by name asc ")
    LiveData<List<Episodes>> getAllEpisodes();

    @Query("select * from Episodes where center_name= :center_name order by name asc ")
    LiveData<List<Episodes>> getEpisodesByCenter(String center_name);

    @Query("select * from Episodes where admin_name= :admin_name order by name asc ")
    LiveData<List<Episodes>> getEpisodesByAdmin(String admin_name);

}
