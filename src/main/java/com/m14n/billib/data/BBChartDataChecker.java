package com.m14n.billib.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.m14n.billib.data.model.BBChart;
import com.m14n.billib.data.model.BBChartMetadata;
import com.m14n.billib.data.model.BBJournalMetadata;
import com.m14n.billib.data.model.BBPositionInfo;
import com.m14n.billib.data.model.BBTrack;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class BBChartDataChecker {
    private static Date TODAY;

    static {
        try {
            TODAY = BB.CHART_DATE_FORMAT.parse("2018-07-21");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) throws IOException, ParseException {
        final Gson theGson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        final File theMetadataFile = new File(BB.DATA_ROOT, "metadata_billboard.json");
        final BBJournalMetadata theMetadata =
                theGson.fromJson(new FileReader(theMetadataFile), BBJournalMetadata.class);
        final Calendar theCalendar = Calendar.getInstance();

        for (final BBChartMetadata theChartMetadata : theMetadata.getCharts()) {
            final File theChartFolder = new File(BB.DATA_ROOT, theChartMetadata.getFolder());
            theCalendar.setTime(BB.CHART_DATE_FORMAT.parse("2018-06-23"));
            BBChart thePreviousChart = null;
            while (theCalendar.getTime().compareTo(TODAY) <= 0) {
                String theDate = BB.CHART_DATE_FORMAT.format(theCalendar.getTime());
                String theFileName = theChartMetadata.getPrefix() + "-" + theDate + ".json";
                File theFile = new File(theChartFolder, theFileName);
                BBChart theChart = null;
                if (theFile.exists()) {
                    Reader theReader = new FileReader(theFile);
                    try {
                        theChart = theGson.fromJson(theReader, BBChart.class);
                        if (thePreviousChart != null) {
                            if (!checkConsistency(thePreviousChart, theChart)) {
                                System.out.println(String.format("=========== ERROR ========== %s %s ",
                                        theChartMetadata.getName(), theDate));
                            }
                        }
                    } finally {
                        theReader.close();
                    }
                } else {
                    System.out.println(String.format("%s DOES NOT EXIST!", theFileName));
                }
                thePreviousChart = theChart;
                theCalendar.add(Calendar.DATE, 7);
            }
        }
    }

    private static boolean checkConsistency(BBChart previousChart, BBChart chart) {
        boolean theResult = true;
        for (BBTrack theTrack : chart.getTracks()) {
            BBPositionInfo thePositionInfo = theTrack.getPositionInfo();
            final int theLastWeek =
                    BB.extractLastWeekRank(thePositionInfo == null ? null : thePositionInfo.getLastWeek());
            if (theLastWeek > 0 && previousChart.getTracks().size() >= theLastWeek) {
                BBTrack thePreviousTrack = previousChart.getTracks().get(theLastWeek - 1);
                if (!(theTrack.getTitle().toLowerCase().equals(thePreviousTrack.getTitle().toLowerCase()) &&
                        theTrack.getArtist().toLowerCase().equals(thePreviousTrack.getArtist().toLowerCase()))) {
                    System.out.println(String.format("CHECK %d", theTrack.getRank()));
                    theResult = false;
                }
            }
        }
        return theResult;
    }
}
