package com.m14n.billib.data.html;

import com.m14n.billib.data.BB;
import com.m14n.billib.data.model.BBChart;
import com.m14n.billib.data.model.BBTrack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.m14n.ex.Ex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BBChartChecker {
    private static Date TODAY;

    static {
        try {
            TODAY = BB.CHART_DATE_FORMAT.parse("2016-12-10");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) throws IOException {
        final Gson theGson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        final File theOldRoot = new File(BB.DATA_ROOT + File.separator + "country-songs");
        final File theNewRoot = new File(BB.DATA_ROOT + "_new" + File.separator + "country-songs");

        final Calendar theCalendar = Calendar.getInstance();
        theCalendar.setTime(TODAY);

        int theStop = 0;
        while (theStop < 2) {
            final String theFormatDate = BB.CHART_DATE_FORMAT.format(theCalendar.getTime());
            System.out.print(theFormatDate + " ");
            final File theOldFile = new File(theOldRoot, theFormatDate + ".json");
            final File theNewFile = new File(theNewRoot, "country-" + theFormatDate + ".json");
            if (theOldFile.exists() && theNewFile.exists()) {
                final BBChart theOldChart = readOld(theGson, theOldFile, theFormatDate);
                final BBChart theNewChart = readNew(theGson, theNewFile);
                if (theNewChart.equals(theOldChart)) {
                    System.out.println("EQUEALS");
                } else {
                    System.out.println("DIFFERENT!!!");
                    break;
                }
            } else {
                System.out.println("NO FILES!");
                theStop++;
            }
            theCalendar.add(Calendar.DATE, -7);
        }
    }

    private static BBChart readOld(Gson gson, File oldChart, String date) throws IOException {
        final BBChart theChart = new BBChart();
        theChart.setName("Country");
        theChart.setDate(date);
        List<BBTrack> theTracks = new ArrayList<>();
        final JsonReader theReader = new JsonReader(new FileReader(oldChart));
        theReader.beginArray();
        while (theReader.hasNext()) {
            BBTrack theTrack = gson.fromJson(theReader, BBTrack.class);
            theTrack.setCoverUrl(Ex.addHttpIfNeeded(theTrack.getCoverUrl()));
            theTracks.add(theTrack);
        }
        theChart.setTracks(theTracks);
        return theChart;
    }

    private static BBChart readNew(Gson gson, File newChart) throws FileNotFoundException {
        return gson.fromJson(new FileReader(newChart), BBChart.class);
    }
}
