package com.android.management.databeas.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.management.model.Branch;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface BranchDAO {

    @Insert()
    void insertBranch(ArrayList<Branch> model);

    @Insert()
    long insertBranch(Branch model);

    @Update()
    int updateBranch(Branch model);

    @Delete
    int deleteBranch(Branch model);

    @Query("select name from branch order by name asc ")
    List<String> getAllBranchName();

    @Query("select * from branch order by name asc ")
    LiveData<List<Branch>> getAllBranch();

}
