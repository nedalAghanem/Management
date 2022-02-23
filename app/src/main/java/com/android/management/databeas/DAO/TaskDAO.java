package com.android.management.databeas.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.management.model.Branch;
import com.android.management.model.Task;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDAO {

    @Insert()
    void insertTask(ArrayList<Task> model);

    @Insert()
    long insertTask(Task model);

    @Update()
    int updateTask(Task model);

    @Delete
    int deleteTask(Task model);


    @Query("select * from Task where student_name = :student_name")
    LiveData<List<Task>> getAllTaskByName(String student_name);

    @Query("select * from Task ")
    LiveData<List<Task>> getAllTask();

}
