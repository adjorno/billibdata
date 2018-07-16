package com.m14n.billib.data.html;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.m14n.billib.data.BB;
import com.m14n.billib.data.model.BBChart;
import com.m14n.billib.data.model.BBChartMetadata;
import com.m14n.billib.data.model.BBJournalMetadata;
import com.m14n.billib.data.model.BBTrack;

import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileReader;
import java.util.Date;

public class OneChartReader {
    public static String DATE = "2017-09-30";
    public static int CHART = 0;

    public static void main(String[] args) throws Exception {
        final Gson theGson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        final File theRoot = new File(BB.DATA_ROOT);
        final File theMetadataFile = new File(theRoot, "metadata_billboard.json");
        BBJournalMetadata theMetadata = theGson.fromJson(new FileReader(theMetadataFile), BBJournalMetadata.class);

        BBChartMetadata theChartMetadata = theMetadata.getCharts().get(CHART);

        Document theLastChartDocument = BBHtmlParser.getChartDocument(theMetadata, theChartMetadata, null);
        Date theLastWeek = BBHtmlParser.getChartDate(theLastChartDocument);
        final BBChart theBBChart =
                BBHtmlReader.readChart(theMetadata, theChartMetadata, BB.CHART_DATE_FORMAT.format(theLastWeek));

        for (BBTrack theBBTrack : theBBChart.getTracks()) {
            System.out.println(theBBTrack);
        }
    }
}
