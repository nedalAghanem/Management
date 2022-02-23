package com.android.management.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.android.management.helpers.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity
@TypeConverters({DateConverter.class})
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String student_name;
    private String episodes_name;
    private String center_name;
    private String host_name;
    private String tester_name;
    private Date task_end;
    private String from;
    private String to;
    private String type;
    private int evaluation;
    private String notes;

    public Task() {
    }

    public Task(int id, String student_name, String episodes_name, String center_name,
                String host_name, String tester_name, Date task_end, String from, String to, String type,
                int evaluation, String notes) {
        this.id = id;
        this.student_name = student_name;
        this.episodes_name = episodes_name;
        this.center_name = center_name;
        this.host_name = host_name;
        this.tester_name = tester_name;
        this.task_end = task_end;
        this.from = from;
        this.to = to;
        this.type = type;
        this.evaluation = evaluation;
        this.notes = notes;
    }

    public Task(String student_name, String episodes_name, String center_name, String host_name,
                String tester_name, Date task_end, String from, String to,
                String type, int evaluation, String notes) {
        this.student_name = student_name;
        this.episodes_name = episodes_name;
        this.center_name = center_name;
        this.host_name = host_name;
        this.tester_name = tester_name;
        this.task_end = task_end;
        this.from = from;
        this.to = to;
        this.type = type;
        this.evaluation = evaluation;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getEpisodes_name() {
        return episodes_name;
    }

    public void setEpisodes_name(String episodes_name) {
        this.episodes_name = episodes_name;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getTester_name() {
        return tester_name;
    }

    public void setTester_name(String tester_name) {
        this.tester_name = tester_name;
    }

    public Date getTask_end() {
        return task_end;
    }

    public void setTask_end(Date task_end) {
        this.task_end = task_end;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
