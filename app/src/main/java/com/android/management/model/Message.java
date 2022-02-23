package com.android.management.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.android.management.helpers.DateConverter;

import java.util.Date;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = {"id"},
                childColumns = {"sender_id"},
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE)

})
@TypeConverters({DateConverter.class})
public class Message {

    @PrimaryKey
    private int id;
    private String text;
    private int sender_id;
    private Date date;

    public Message() {
    }

    public Message(int id, String text, int sender_id, Date date) {
        this.id = id;
        this.text = text;
        this.sender_id = sender_id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
