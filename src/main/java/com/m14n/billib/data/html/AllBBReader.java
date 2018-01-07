package com.m14n.billib.data.html;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.m14n.billib.data.BB;
import com.m14n.billib.data.model.BBChart;
import com.m14n.billib.data.model.BBChartMetadata;
import com.m14n.billib.data.model.BBJournalMetadata;
import com.m14n.billib.data.model.BBTrack;
import com.m14n.ex.Ex;

import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AllBBReader {
    private static final int POSSIBLE_SKIPPED_WEEKS_IN_ROW = 5;
    private static Date TODAY;

    static {
        try {
            TODAY = BB.CHART_DATE_FORMAT.parse("2018-01-13");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) throws FileNotFoundException {
        final Gson theGson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        final File theRoot = new File(BB.DATA_ROOT);
        final File theMetadataFile = new File(theRoot, "metadata_billboard.json");
        BBJournalMetadata theMetadata = theGson.fromJson(new FileReader(theMetadataFile), BBJournalMetadata.class);

        for (BBChartMetadata theChartMetadata : theMetadata.getCharts()) {
            fetchChart(theGson, theRoot, theMetadata, theChartMetadata);
        }
    }

    private static void fetchChart(Gson gson, File root, BBJournalMetadata metadata, BBChartMetadata theChartMetadata) {
        System.out.println("---------------------" + theChartMetadata.getName() + "----------------------------");
        final File theChartDir = new File(root, theChartMetadata.getFolder());
        if (!theChartDir.exists()) {
            theChartDir.mkdirs();
        }
        int theSkip = 0;
        final Calendar theCalendar = Calendar.getInstance();
        theCalendar.setTime(TODAY);
        while (theSkip <= POSSIBLE_SKIPPED_WEEKS_IN_ROW) {
            String theCurrent = BB.CHART_DATE_FORMAT.format(theCalendar.getTime());
            theCalendar.add(Calendar.DATE,
                    "2018-01-06".equals(theCurrent) ? -3 : "2018-01-03".equals(theCurrent) ? -4 : -7);
            final String theFormatDate = BB.CHART_DATE_FORMAT.format(theCalendar.getTime());
            System.out.print(theChartMetadata.getName() + " " + theFormatDate + " ");
            final File theChartFile = new File(theChartDir,
                    theChartMetadata.getPrefix() + "-" + theFormatDate + ".json");
            if (!theChartFile.exists()) {
                try {
                    final Document theChartDocument = BBHtmlParser.getChartDocument(metadata, theChartMetadata,
                            theFormatDate);
                    if (!theFormatDate.equals(BBHtmlParser.getChartDate(theChartDocument))) {
                        System.out.println("WRONG DATE!");
                        theSkip++;
                        continue;
                    }
                    List<BBTrack> theTracks = null;
                    try {
                        theTracks = BBHtmlParser.getTracks(theChartDocument);
                    } catch (Exception e) {
                        e.printStackTrace();
                        theSkip++;
                        continue;
                    }
                    final int theTracksSize = theTracks.size();
                    if (theTracksSize != theChartMetadata.getSize()) {
                        System.out.print("SIZE = " + theTracksSize + " EXPECTED = " + theChartMetadata.getSize() + " ");
                    }
                    final BBChart theChart = new BBChart();
                    theChart.setName(theChartMetadata.getName());
                    theChart.setDate(theFormatDate);
                    theChart.setTracks(theTracks);

                    final FileWriter theWriter = new FileWriter(theChartFile);
                    gson.toJson(theChart, theWriter);
                    Ex.closeSilently(theWriter);
                    System.out.println("SUCCESS!");
                    theSkip = 0;
                } catch (IOException e) {
                    e.printStackTrace();
                    theSkip++;
                }
            } else {
                theSkip = 0;
                System.out.println("ALREADY EXISTS");
            }
        }
    }
}
