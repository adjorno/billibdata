package com.m14n.billib.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BBJournalMetadata {

    @SerializedName("name")
    private String mName;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("base_rss")
    private String mBaseRss;

    @SerializedName("charts")
    private List<BBChartMetadata> mCharts;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public List<BBChartMetadata> getCharts() {
        return mCharts;
    }

    public void setCharts(List<BBChartMetadata> charts) {
        mCharts = charts;
    }

    public String getBaseRss() {
        return mBaseRss;
    }

    public void setBaseRss(String baseRss) {
        mBaseRss = baseRss;
    }

}
