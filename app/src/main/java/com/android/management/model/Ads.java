package com.android.management.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.android.management.helpers.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Center.class,
                parentColumns = {"name"},
                childColumns = {"center_name"},
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(
                entity = Episodes.class,
                parentColumns = {"name"},
                childColumns = {"episode_name"},
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE)

})
@TypeConverters({DateConverter.class})
public class Ads implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String content;
    private String publisher_name;
    private String center_name;
    private String episode_name;
    private String photo;

    public Ads() {
    }

    public Ads(int id, Date date, String content, String publisher_name,
               String center_name, String episode_nam, String photo) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.publisher_name = publisher_name;
        this.center_name = center_name;
        this.episode_name = episode_name;
        this.photo = photo;
    }

    public Ads(Date date, String content, String publisher_name, String center_name,
               String episode_name, String photo) {
        this.date = date;
        this.content = content;
        this.publisher_name = publisher_name;
        this.center_name = center_name;
        this.episode_name = episode_name;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public String getEpisode_name() {
        return episode_name;
    }

    public void setEpisode_name(String episode_name) {
        this.episode_name = episode_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
