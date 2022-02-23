package com.android.management.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.android.management.helpers.DateConverter;

@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class Branch {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public Branch() {
    }

    public Branch(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Branch(@NonNull String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
