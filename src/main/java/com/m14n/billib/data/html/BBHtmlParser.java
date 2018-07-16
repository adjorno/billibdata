package com.m14n.billib.data.html;

import com.m14n.billib.data.BB;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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

    private static Element getHeader(Document document) {
        Element theMain = document.body().getElementById("main");
        return theMain.getElementsByClass("chart-detail-header").first();
    }

    public static Date getChartDate(Document document) throws ParseException {
        Element theHeader = getHeader(document);
        Element theContainer = theHeader.getElementsByClass("chart-detail-header__chart-info").first();
        Element theDetails = theContainer.getElementsByClass("chart-detail-header__select-date").first();
        Element theDropdown = theDetails.getElementsByClass("chart-detail-header__date-selector").first();
        Element theButton = theDropdown.getElementsByClass("chart-detail-header__date-selector-button").first();
        return BB.CHART_DATE_HTML_FORMAT.parse(theButton.text());
    }

    public static List<BBTrack> getTracks(Document document) {
        final List<BBTrack> theTracks = new ArrayList<>();
        theTracks.add(getLeader(getHeader(document)));

        Elements itemLists = document.body()
                .getElementsByClass("chart-container").first()
                .getElementsByClass("chart-details").first()
                .getElementsByClass("chart-list");
        for (Element list : itemLists) {
            Elements items = list.getElementsByClass("chart-list-item");
            for (Element item : items) {
                theTracks.add(getTrack(item));
            }
        }
        return theTracks;
    }

    private static BBTrack getLeader(Element header) {
        BBTrack theLeaderTrack = new BBTrack();
        theLeaderTrack.setRank(1);
        Element theLeaderInfo = header.getElementsByClass("chart-number-one").first()
                .getElementsByClass("chart-number-one__info").first();
        Element theDetails = theLeaderInfo.getElementsByClass("chart-number-one__details").first();
        theLeaderTrack.setTitle(theDetails.getElementsByClass("chart-number-one__title").first().text());
        theLeaderTrack.setArtist(theDetails.getElementsByClass("chart-number-one__artist").first().text());
        BBPositionInfo thePositionInfo = new BBPositionInfo();
        theLeaderTrack.setPositionInfo(thePositionInfo);
        Element theStats = theLeaderInfo.getElementsByClass("chart-number-one__stats").first();
        Element theLastWeekElement = theStats.getElementsByClass("chart-number-one__last-week").first();
        if (theLastWeekElement != null) {
            thePositionInfo.setLastWeek(theLastWeekElement.text());
        }
        thePositionInfo.setPeekPosition(1);
        thePositionInfo.setWksOnChart(
                Integer.valueOf(theStats.getElementsByClass("chart-number-one__weeks-on-chart").first().text()));
        return theLeaderTrack;
    }

    private static BBTrack getTrack(Element row) {
        BBTrack theTrack = new BBTrack();
        theTrack.setRank(Integer.valueOf(row.attr("data-rank")));
        theTrack.setArtist(row.attr("data-artist"));
        theTrack.setTitle(row.attr("data-title"));
        Element theExtraInfo = row.getElementsByClass("chart-list-item__extra-info").first();
        Element theStats = theExtraInfo.getElementsByClass("chart-list-item__stats").first();
        if (theStats != null) {
            BBPositionInfo thePositionInfo = new BBPositionInfo();
            String theLastWeek = theStats.getElementsByClass("chart-list-item__last-week").first().text();
            thePositionInfo.setLastWeek(theLastWeek);
            String thePeek = theStats.getElementsByClass("chart-list-item__weeks-at-one").first().text();
            thePositionInfo.setPeekPosition(Integer.valueOf(thePeek));
            String theWeeks = theStats.getElementsByClass("chart-list-item__weeks-on-chart").first().text();
            thePositionInfo.setWksOnChart(Integer.valueOf(theWeeks));
            theTrack.setPositionInfo(thePositionInfo);
        }

        return theTrack;
    }
}
