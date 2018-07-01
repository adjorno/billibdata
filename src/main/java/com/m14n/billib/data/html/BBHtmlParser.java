package com.m14n.billib.data.html;

import com.m14n.billib.data.model.BBChartMetadata;
import com.m14n.billib.data.model.BBJournalMetadata;
import com.m14n.billib.data.model.BBPositionInfo;
import com.m14n.billib.data.model.BBTrack;
import com.m14n.ex.Ex;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BBHtmlParser {

    public static Document getChartDocument(BBJournalMetadata metadata, BBChartMetadata chart, String date)
            throws IOException {
        String theUrl = metadata.getUrl() + chart.getFolder();
        if (Ex.isNotEmpty(date)) {
            theUrl += "/" + date;
        }
        final Document theDocument = Jsoup.connect(theUrl).userAgent("Mozilla").timeout(10 * 1000).get();
        return theDocument;
    }

    public static String getChartDate(Document document) {
        return document.body().getElementById("main").getElementsByClass("js-chart-data").attr("data-chartdate");
    }

    public static List<BBTrack> getTracks(Document document) {
        final Element theChartData = document.body().getElementById("main").getElementsByClass("js-chart-data").first();
        final Elements theChartRowContainers = theChartData.getElementsByClass("container");
        final List<BBTrack> theTracks = new ArrayList<>();
        for (Element theChartRowContainer : theChartRowContainers) {
            theTracks.addAll(getTracksFromContainer(theChartRowContainer));
        }
        return theTracks;
    }

    private static List<BBTrack> getTracksFromContainer(Element container) {
        final List<BBTrack> theTracks = new ArrayList<>();
        Elements theChartRows = container.getElementsByClass("js-chart-row");
        for (Element theChartRow : theChartRows) {
            BBTrack theTrack = getTrack(theChartRow);
            theTracks.add(theTrack);
        }
        return theTracks;
    }

    private static BBTrack getTrack(Element row) {
        final Element theMainDisplay = row.getElementsByClass("chart-row__primary").first().getElementsByClass(
                "chart-row__main-display").first();
        BBTrack theTrack = new BBTrack();
        theTrack.setArtist(theMainDisplay.getElementsByClass("chart-row__artist").text());
        theTrack.setTitle(theMainDisplay.getElementsByClass("chart-row__song").text());
        theTrack.setRank(getRank(theMainDisplay.getElementsByClass("chart-row__rank").first()));
        theTrack.setCoverUrl(getCoverUrl(theMainDisplay.getElementsByClass("chart-row__image").first()));
        theTrack.setSpotifyUrl(getSpotifyUrl(theMainDisplay));
        final Element theSecondary = row.getElementsByClass("chart-row__secondary").first();
        if (theSecondary != null) {
            BBPositionInfo thePositionInfo = new BBPositionInfo();
            final Element theLastWeekElement = theSecondary.getElementsByClass("chart-row__last-week").first();
            thePositionInfo.setLastWeek(getPosition(theLastWeekElement));
            final String thePeekPosition = getPosition(theSecondary.getElementsByClass("chart-row__top-spot").first());
            if (Ex.isNotEmpty(thePeekPosition)) {
                thePositionInfo.setPeekPosition(Integer.valueOf(thePeekPosition));
            }
            final String theWeeks = getPosition(
                    theSecondary.getElementsByClass("chart-row__weeks-on-chart").first());
            if (Ex.isNotEmpty(theWeeks)) {
                thePositionInfo.setWksOnChart(Integer.valueOf(theWeeks));
            }
            theTrack.setPositionInfo(thePositionInfo);
        }
        return theTrack;
    }

    private static String getPosition(Element secondaryRank) {
        return secondaryRank.getElementsByClass("chart-row__value").text();
    }

    private static int getRank(Element rankElement) {
        return Integer.valueOf(rankElement.getElementsByClass("chart-row__current-week").text().trim());
    }

    private static String getCoverUrl(Element coverElement) {
        String theResult = coverElement.attr("data-imagesrc");
        if (Ex.isEmpty(theResult)) {
            final String theStyle = coverElement.attr("style");
            if (!Ex.isEmpty(theStyle)) {
                theResult = theStyle.substring(theStyle.indexOf("http"), theStyle.length() - 1);
            }
        }
        return Ex.isEmpty(theResult) ? null : theResult;
    }

    private static String getSpotifyUrl(Element mainDisplay) {
        final String theUrl = mainDisplay.getElementsByClass("chart-row__link--spotify").attr("data-href");
        return Ex.isEmpty(theUrl) ? null : theUrl;
    }
}
