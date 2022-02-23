package com.android.management.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.android.management.helpers.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(indices = {@Index(value = {"p_id", "email"}, unique = true)})
@TypeConverters({DateConverter.class})
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String p_id;
    private String fullName;
    private String email;
    private String phone;
    //@Ignore // تجاهل
    private Date birthDate;
    private String address;
    private String branch_name;
    private String episode_name;
    private String center_name;
    private String password;
    private Validity validity;
    private String photo;

    public User() {
    }

    public User(int id, String p_id, String fullName, String email, String phone, Date birthDate,
                String address, String branch_name, String episode_name, String center_name,
                String password, Validity validity, String photo) {
        this.id = id;
        this.p_id = p_id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.address = address;
        this.branch_name = branch_name;
        this.episode_name = episode_name;
        this.center_name = center_name;
        this.password = password;
        this.validity = validity;
        this.photo = photo;
    }

    public User(String p_id, @NonNull String fullName, @NonNull String email,
                String phone, Date birthDate, String address,
                String branch_name, String center_name, String episode_name, @NonNull String password,
                @NonNull Validity validity, String photo) {
        this.p_id = p_id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.address = address;
        this.branch_name = branch_name;
        this.episode_name = episode_name;
        this.center_name = center_name;
        this.password = password;
        this.validity = validity;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    @NonNull
    public String getFullName() {
        return fullName;
    }

    public void setFullName(@NonNull String fullName) {
        this.fullName = fullName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getEpisode_name() {
        return episode_name;
    }

    public void setEpisode_name(String episode_name) {
        this.episode_name = episode_name;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public Validity getValidity() {
        return validity;
    }

    public void setValidity(@NonNull Validity validity) {
        this.validity = validity;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
