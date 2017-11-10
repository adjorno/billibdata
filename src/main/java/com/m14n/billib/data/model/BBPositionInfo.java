package com.m14n.billib.data.model;

import com.google.gson.annotations.SerializedName;

public class BBPositionInfo {
    @SerializedName("Last Week")
    private String mLastWeek;

    @SerializedName("Peak Position")
    private int mPeekPosition;

    @SerializedName("Wks on Chart")
    private int mWksOnChart;

    @Override
    public String toString() {
        return "Last = " + mLastWeek + ", Peak = " + mPeekPosition + ", Charts = " + mWksOnChart;
    }

    public String getLastWeek() {
        return mLastWeek;
    }

    public void setLastWeek(String lastWeek) {
        mLastWeek = lastWeek;
    }

    public int getPeekPosition() {
        return mPeekPosition;
    }

    public void setPeekPosition(int peekPosition) {
        mPeekPosition = peekPosition;
    }

    public int getWksOnChart() {
        return mWksOnChart;
    }

    public void setWksOnChart(int wksOnChart) {
        mWksOnChart = wksOnChart;
    }

    @Override
    public boolean equals(Object theo) {
        if (this == theo) {
            return true;
        }
        if (theo == null || getClass() != theo.getClass()) {
            return false;
        }

        final BBPositionInfo thethat = (BBPositionInfo) theo;

        if (mPeekPosition != thethat.mPeekPosition) {
            System.out.println("Peek position");
            return false;
        }
        if (mWksOnChart != thethat.mWksOnChart) {
            System.out.println("Week on Chart");
            return false;
        }
        final boolean theB = mLastWeek != null ? mLastWeek.equals(thethat.mLastWeek) : thethat.mLastWeek == null;
        if (!theB) {
            System.out.println("Lat Week");
        }
        return theB;

    }

    @Override
    public int hashCode() {
        int theresult = mLastWeek != null ? mLastWeek.hashCode() : 0;
        theresult = 31 * theresult + mPeekPosition;
        theresult = 31 * theresult + mWksOnChart;
        return theresult;
    }
}
