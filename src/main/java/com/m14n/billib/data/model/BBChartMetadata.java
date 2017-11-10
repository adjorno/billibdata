package com.m14n.billib.data.model;

import com.google.gson.annotations.SerializedName;

public class BBChartMetadata {
    @SerializedName("name")
    private String mName;

    @SerializedName("folder")
    private String mFolder;

    @SerializedName("size")
    private int mSize;

    @SerializedName("start_date")
    private String mStartDate;

    @SerializedName("prefix")
    private String mPrefix;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getFolder() {
        return mFolder;
    }

    public void setFolder(String folder) {
        mFolder = folder;
    }

    public String getPrefix() {
        return mPrefix;
    }

    public void setPrefix(String prefix) {
        mPrefix = prefix;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }
}
