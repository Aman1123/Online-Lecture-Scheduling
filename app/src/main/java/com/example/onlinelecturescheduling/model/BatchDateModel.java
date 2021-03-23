package com.example.onlinelecturescheduling.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BatchDateModel implements Serializable {


    public BatchDateModel(String batchName, String batchDate) {
        this.batchName = batchName;
        this.batchDate = batchDate;
    }

    public BatchDateModel() {

    }

    @SerializedName("batchName")
    @Expose
    private String batchName;
    @SerializedName("batchDate")
    @Expose
    private String batchDate;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(String batchDate) {
        this.batchDate = batchDate;
    }

}
