package com.m14n.billib.data.html;

import com.m14n.billib.data.BB;
import com.m14n.billib.data.model.BBChart;
import com.m14n.billib.data.model.BBChartMetadata;
import com.m14n.billib.data.model.BBJournalMetadata;
import com.m14n.billib.data.model.BBTrack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OneChartReader {
    public static String DATE = "2017-09-30";
    public static int CHART = 0;

    public static void main(String[] args) throws IOException {
        final Gson theGson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        final File theRoot = new File(BB.DATA_ROOT);
        final File theMetadataFile = new File(theRoot, "metadata_billboard.json");
        BBJournalMetadata theMetadata = theGson.fromJson(new FileReader(theMetadataFile), BBJournalMetadata.class);

        BBChartMetadata theChartMetadata = theMetadata.getCharts().get(CHART);

        final BBChart theBBChart = BBHtmlReader.readChart(theMetadata, theChartMetadata, DATE);

        for (BBTrack theBBTrack : theBBChart.getTracks()) {
            System.out.println(theBBTrack);
        }
    }
}
