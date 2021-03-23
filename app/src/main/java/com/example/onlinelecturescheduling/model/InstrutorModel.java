package com.example.onlinelecturescheduling.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstrutorModel {

    @SerializedName("Instructor_Name")
    @Expose
    private String instructorName;
    @SerializedName("Instructor_Username")
    @Expose
    private String instructorUsername;
    @SerializedName("Instructor_Password")
    @Expose
    private String instructorPassword;
    @SerializedName("Instructor_ID")
    @Expose
    private String instructorID;

    public InstrutorModel() {
    }

    public InstrutorModel(String instructorName, String instructorUsername, String instructorPassword, String instructorID) {
        this.instructorName = instructorName;
        this.instructorUsername = instructorUsername;
        this.instructorPassword = instructorPassword;
        this.instructorID = instructorID;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorUsername() {
        return instructorUsername;
    }

    public void setInstructorUsername(String instructorUsername) {
        this.instructorUsername = instructorUsername;
    }

    public String getInstructorPassword() {
        return instructorPassword;
    }

    public void setInstructorPassword(String instructorPassword) {
        this.instructorPassword = instructorPassword;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }
}
