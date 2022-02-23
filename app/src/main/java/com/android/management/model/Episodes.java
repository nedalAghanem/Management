package com.android.management.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        foreignKeys = {
//                @ForeignKey(
//                        entity = User.class,
//                        parentColumns = {"fullName"},
//                        childColumns = {"admin_name"},
//                        onUpdate = ForeignKey.CASCADE,
//                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = Center.class,
                        parentColumns = {"name"},
                        childColumns = {"center_name"},
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = Branch.class,
                        parentColumns = {"name"},
                        childColumns = {"branch_name"},
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.CASCADE)

        }, indices = {@Index(value = {"name"}, unique = true)}
)
public class Episodes implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String admin_name;
    private String center_name;
    private String branch_name;
    private String numberStudents;
    private String description;
    private String address;
    private String photo;

    public Episodes() {
    }

    public Episodes(int id, String name, String admin_name, String center_name,
                    String branch_name, String numberStudents, String description,
                    String address, String photo) {
        this.id = id;
        this.name = name;
        this.admin_name = admin_name;
        this.center_name = center_name;
        this.branch_name = branch_name;
        this.numberStudents = numberStudents;
        this.description = description;
        this.address = address;
        this.photo = photo;
    }

    public Episodes(String name, String admin_name, String center_name,
                    String branch_name, String numberStudents,
                    String description, String address, String photo) {
        this.name = name;
        this.admin_name = admin_name;
        this.center_name = center_name;
        this.branch_name = branch_name;
        this.numberStudents = numberStudents;
        this.description = description;
        this.address = address;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getNumberStudents() {
        return numberStudents;
    }

    public void setNumberStudents(String numberStudents) {
        this.numberStudents = numberStudents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
