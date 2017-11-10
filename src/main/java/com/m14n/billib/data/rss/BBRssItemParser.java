package com.m14n.billib.data.rss;

import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.io.WireFeedParser;
import com.sun.syndication.io.impl.RSS20Parser;

import org.jdom.Element;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BBRssItemParser extends RSS20Parser implements WireFeedParser {
    private final DateFormat RSS_DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy");

    @Override
    public Item parseItem(Element rssRoot, Element eItem) {
        Item theItem = super.parseItem(rssRoot, eItem);
        final BBRssItem theBBItem = new BBRssItem();
        theBBItem.setTitle(theItem.getTitle());
        theBBItem.setDescription(theItem.getDescription());
        Element e = eItem.getChild("artist", getRSSNamespace());
        if (e != null) {
            theBBItem.setArtist(e.getText());
        }
        e = eItem.getChild("chart_item_title", getRSSNamespace());
        if (e != null) {
            theBBItem.setTitle(e.getText());
        }
        e = eItem.getChild("rank_this_week", getRSSNamespace());
        if (e != null) {
            theBBItem.setRank(Integer.valueOf(e.getText()));
        }
        e = eItem.getChild("rank_last_week", getRSSNamespace());
        if (e != null) {
            theBBItem.setLastRank(Integer.valueOf(e.getText()));
        }
        e = eItem.getChild("pubDate", getRSSNamespace());
        if (e != null) {
            try {
                theBBItem.setDate(RSS_DATE_FORMAT.parse(e.getText()));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return theBBItem;
    }
}
