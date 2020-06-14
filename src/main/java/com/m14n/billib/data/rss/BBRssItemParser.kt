package com.m14n.billib.data.rss

import com.sun.syndication.feed.rss.Item
import com.sun.syndication.io.WireFeedParser
import com.sun.syndication.io.impl.RSS20Parser

import org.jdom.Element

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

class BBRssItemParser : RSS20Parser(), WireFeedParser {
    private val RSS_DATE_FORMAT = SimpleDateFormat("EEE, dd MMM yyyy")

    override fun parseItem(rssRoot: Element?, eItem: Element): Item {
        val theItem = super.parseItem(rssRoot, eItem)
        val theBBItem = BBRssItem()
        theBBItem.setTitle(theItem.title)
        theBBItem.description = theItem.description
        var e: Element? = eItem.getChild("artist", rssNamespace)
        if (e != null) {
            theBBItem.artist = e.text
        }
        e = eItem.getChild("chart_item_title", rssNamespace)
        if (e != null) {
            theBBItem.setTitle(e.text)
        }
        e = eItem.getChild("rank_this_week", rssNamespace)
        if (e != null) {
            theBBItem.rank = Integer.valueOf(e.text)
        }
        e = eItem.getChild("rank_last_week", rssNamespace)
        if (e != null) {
            theBBItem.lastRank = Integer.valueOf(e.text)
        }
        e = eItem.getChild("pubDate", rssNamespace)
        if (e != null) {
            try {
                theBBItem.date = RSS_DATE_FORMAT.parse(e.text)
            } catch (e1: ParseException) {
                e1.printStackTrace()
            }

        }
        return theBBItem
    }
}
