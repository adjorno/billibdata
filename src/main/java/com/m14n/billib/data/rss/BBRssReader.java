package com.m14n.billib.data.rss;

import com.m14n.billib.data.BB;
import com.m14n.billib.data.model.BBChartMetadata;
import com.m14n.billib.data.model.BBJournalMetadata;
import com.m14n.billib.data.model.BBTrack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.m14n.ex.BenchmarkCore;
import com.m14n.ex.Ex;
import com.sun.syndication.feed.WireFeed;
import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.WireFeedInput;
import com.sun.syndication.io.XmlReader;

import org.jdom.Document;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BBRssReader {


    public static void main(String... args) throws Exception {
        final File theMetadataFile = new File(BB.DATA_ROOT + File.separator + "metadata_billboard.json");
        BBJournalMetadata theMetadata = new Gson().fromJson(new FileReader(theMetadataFile), BBJournalMetadata.class);

        final WireFeedInput theRssInput = new WireFeedInput() {
            @Override
            public WireFeed build(Document document) throws IllegalArgumentException, FeedException {
                return new BBRssItemParser().parse(document, false);
            }
        };

        File theWeekFolder = null;
        String theWeekDate = null;
        final Gson theGson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        for (BBChartMetadata theChart : theMetadata.getCharts()) {
            final int theBenchmark = BenchmarkCore.start(theChart.getFolder());
            final URL theRssSource = new URL(theMetadata.getBaseRss() + theChart.getFolder());
            final Channel feed = (Channel) theRssInput.build(new XmlReader(theRssSource));
            final List<BBRssItem> theItems = feed.getItems();
            final List<BBTrack> theTracks = new ArrayList<>();
            for (BBRssItem theRssItem : theItems) {
                BBTrack theTrack = new BBTrack();
                theTrack.setRank(theRssItem.getRank());
                theTrack.setTitle(theRssItem.getTitle());
                theTrack.setArtist(theRssItem.getArtist());
                if (Ex.isEmpty(theWeekDate)) {
                    theWeekDate = BB.CHART_DATE_FORMAT.format(theRssItem.getDate());
                    theWeekFolder = new File(BB.DATA_ROOT + File.separator + "week-" + theWeekDate);
                    theWeekFolder.mkdirs();
                }
                System.out.println(theTrack);
                theTracks.add(theTrack);
            }
            BenchmarkCore.stop(theBenchmark);
            final File theChartFile = new File(theWeekFolder, theChart.getPrefix() + "-" + theWeekDate + ".json");
            final FileWriter theWriter = new FileWriter(theChartFile);
            theGson.toJson(theTracks, theWriter);
            Ex.closeSilently(theWriter);
            break;
        }
    }
}
