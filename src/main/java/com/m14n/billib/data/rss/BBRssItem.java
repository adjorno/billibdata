package com.m14n.billib.data.rss;

import com.sun.syndication.feed.rss.Item;

import java.util.Date;

public class BBRssItem extends Item {
    private String mArtist;
    private String mTitle;
    private int mRank;
    private int mLastRank;
    private Date mDate;

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public void setTitle(String title) {
        mTitle = title;
    }

    public int getRank() {
        return mRank;
    }

    public void setRank(int rank) {
        mRank = rank;
    }

    public int getLastRank() {
        return mLastRank;
    }

    public void setLastRank(int lastRank) {
        mLastRank = lastRank;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
