package com.m14n.billib.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BBChart {
    @SerializedName("name")
    private String mName;

    @SerializedName("chart_date")
    private String mDate;

    public List<BBTrack> getTracks() {
        return mTracks;
    }

    public void setTracks(List<BBTrack> tracks) {
        mTracks = tracks;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    @SerializedName("tracks")
    private List<BBTrack> mTracks;

    @Override
    public boolean equals(Object theo) {
        if (this == theo) {
            return true;
        }
        if (theo == null || getClass() != theo.getClass()) {
            return false;
        }

        final BBChart thetheChart = (BBChart) theo;

        if (!mName.equals(thetheChart.mName)) {
            System.out.println("Chart Name");
            return false;
        }
        if (!mDate.equals(thetheChart.mDate)) {
            System.out.println("Chart date");
            return false;
        }
        final boolean theEquals = mTracks.equals(thetheChart.mTracks);
        if (!theEquals) {
            System.out.println("Chart Tracks");
        }
        return theEquals;

    }

    @Override
    public int hashCode() {
        int theresult = mName.hashCode();
        theresult = 31 * theresult + mDate.hashCode();
        theresult = 31 * theresult + mTracks.hashCode();
        return theresult;
    }

    @Override
    public String toString() {
        return mName + " " + mDate;
    }
}
