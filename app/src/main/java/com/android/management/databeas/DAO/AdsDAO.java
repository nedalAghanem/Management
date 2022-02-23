package com.android.management.databeas.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.management.model.Ads;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface AdsDAO {

    @Insert()
    void insertAds(ArrayList<Ads> model);

    @Insert()
    long insertAds(Ads model);

    @Update()
    int updateAds(Ads model);

    @Delete
    int deleteAds(Ads model);

    @Query("select * from Ads ")
    LiveData<List<Ads>> getAllAds();

    @Query("select * from Ads where center_name = :center_name ")
    LiveData<List<Ads>> getAllAdsByCenter(String center_name);
}
