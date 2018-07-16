package com.m14n.billib.data.html;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.m14n.billib.data.BB;
import com.m14n.billib.data.model.BBChart;
import com.m14n.billib.data.model.BBChartMetadata;
import com.m14n.billib.data.model.BBJournalMetadata;
import com.m14n.ex.BenchmarkCore;
import com.m14n.ex.Ex;

import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class BBHtmlReader {
    public static void main(String... args) throws Exception {
        final Gson theGson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        final File theMetadataFile = new File(BB.DATA_ROOT + File.separator + "metadata_billboard.json");
        BBJournalMetadata theMetadata = theGson.fromJson(new FileReader(theMetadataFile), BBJournalMetadata.class);

        File theWeekFolder = null;
        Date theWeekDate = null;
        for (BBChartMetadata theChartMetadata : theMetadata.getCharts()) {
            final int theBenchmark = BenchmarkCore.start(theChartMetadata.getFolder());
            final Document theDocument = BBHtmlParser.getChartDocument(theMetadata, theChartMetadata, null);
            if (theWeekDate == null) {
                theWeekDate = BBHtmlParser.getChartDate(theDocument);
                theWeekFolder =
                        new File(BB.DATA_ROOT + File.separator + "week-" + BB.CHART_DATE_FORMAT.format(theWeekDate));
                theWeekFolder.mkdirs();
            }
            final BBChart theChart = new BBChart();
            theChart.setName(theChartMetadata.getName());
            theChart.setDate(BB.CHART_DATE_FORMAT.format(theWeekDate));
            theChart.setTracks(BBHtmlParser.getTracks(theDocument));
            BenchmarkCore.stop(theBenchmark);
            writeChartToFile(theGson, theChart, theWeekFolder,
                    theChartMetadata.getPrefix() + "-" + theWeekDate + ".json");
        }
    }

    public static BBChart readChart(BBJournalMetadata journalMetadata, BBChartMetadata chartMetadata, String week)
            throws IOException {
        final Document theDocument = BBHtmlParser.getChartDocument(journalMetadata, chartMetadata, week);
        final BBChart theChart = new BBChart();
        theChart.setName(chartMetadata.getName());
        theChart.setDate(week);
        theChart.setTracks(BBHtmlParser.getTracks(theDocument));
        return theChart;
    }

    public static void writeChartToFile(Gson gson, BBChart chart, File folder, String fileName) throws IOException {
        final File theChartFile = new File(folder, fileName);
        final FileWriter theWriter = new FileWriter(theChartFile);
        gson.toJson(chart, theWriter);
        Ex.closeSilently(theWriter);

    }
}
