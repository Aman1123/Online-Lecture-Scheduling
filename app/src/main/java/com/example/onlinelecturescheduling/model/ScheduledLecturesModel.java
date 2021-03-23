package com.example.onlinelecturescheduling.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduledLecturesModel {
    @SerializedName("batchDate")
    @Expose
    private String batchDate;
    @SerializedName("batchName")
    @Expose
    private String batchName;
    @SerializedName("courseName")
    @Expose
    private String courseName;

    public ScheduledLecturesModel(String batchDate, String batchName, String courseName) {
        this.batchDate = batchDate;
        this.batchName = batchName;
        this.courseName = courseName;
    }

    public ScheduledLecturesModel() {

    }

    public String getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(String batchDate) {
        this.batchDate = batchDate;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


}
