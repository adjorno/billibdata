package com.m14n.billib.data.rss

import com.sun.syndication.feed.rss.Item

import java.util.Date

class BBRssItem : Item() {
    var artist: String? = null
    private var mTitle: String? = null
    var rank: Int = 0
    var lastRank: Int = 0
    var date: Date? = null

    override fun getTitle(): String? {
        return mTitle
    }

    override fun setTitle(title: String) {
        mTitle = title
    }
}
