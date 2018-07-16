package com.m14n.billib.data.model;

import com.google.gson.annotations.SerializedName;

public class BBTrack {
    @SerializedName("rank")
    private int mRank;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("artist")
    private String mArtist;

    @SerializedName("cover")
    private String mCoverUrl;

    @SerializedName("spotify")
    private String mSpotifyUrl;

    @SerializedName("position")
    private BBPositionInfo mPositionInfo;

    public int getRank() {
        return mRank;
    }

    public void setRank(int rank) {
        mRank = rank;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        mCoverUrl = coverUrl;
    }

    public BBPositionInfo getPositionInfo() {
        return mPositionInfo;
    }

    public void setPositionInfo(BBPositionInfo positionInfo) {
        mPositionInfo = positionInfo;
    }

    public String getSpotifyUrl() {
        return mSpotifyUrl;
    }

    public void setSpotifyUrl(String spotifyUrl) {
        mSpotifyUrl = spotifyUrl;
    }

    @Override
    public String toString() {
        return mRank + ". " + mArtist + " - " + mTitle + " (" + mPositionInfo + ")";
    }

    @Override
    public boolean equals(Object theo) {
        if (this == theo) {
            return true;
        }
        if (theo == null || getClass() != theo.getClass()) {
            return false;
        }

        final BBTrack thethat = (BBTrack) theo;

        if (mRank != thethat.mRank) {
            System.out.println("Track Rank");
            System.out.println(this);
            return false;
        }
        if (mTitle != null ? !mTitle.trim().equals(thethat.mTitle.trim()) : thethat.mTitle != null) {
            System.out.println("Track Title");
            System.out.println(this);
            return false;
        }
        if (mArtist != null ? !mArtist.equals(thethat.mArtist) : thethat.mArtist != null) {
            System.out.println("Track Artist");
            System.out.println(this);
            return false;
        }
        return true;

    }

    @Override
    public int hashCode() {
        int theresult = mRank;
        theresult = 31 * theresult + (mTitle != null ? mTitle.hashCode() : 0);
        theresult = 31 * theresult + (mArtist != null ? mArtist.hashCode() : 0);
        theresult = 31 * theresult + (mCoverUrl != null ? mCoverUrl.hashCode() : 0);
        theresult = 31 * theresult + (mPositionInfo != null ? mPositionInfo.hashCode() : 0);
        return theresult;
    }
}
