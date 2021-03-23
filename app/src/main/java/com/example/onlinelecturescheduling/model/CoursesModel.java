package com.example.onlinelecturescheduling.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoursesModel implements Serializable {

    @SerializedName("courseDescription:")
    @Expose
    private String courseDescription;
    @SerializedName("courseId:")
    @Expose
    private String courseId;
    @SerializedName("courseImg:")
    @Expose
    private String courseImg;
    @SerializedName("courseLvl:")
    @Expose
    private String courseLvl;
    @SerializedName("courseName:")
    @Expose
    private String courseName;

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public CoursesModel() {
    }

    public CoursesModel(String courseId, String courseName, String courseDescription, String courseImg, String courseLvl) {
        this.courseDescription = courseDescription;
        this.courseId = courseId;
        this.courseImg = courseImg;
        this.courseLvl = courseLvl;
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseLvl() {
        return courseLvl;
    }

    public void setCourseLvl(String courseLvl) {
        this.courseLvl = courseLvl;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

}
