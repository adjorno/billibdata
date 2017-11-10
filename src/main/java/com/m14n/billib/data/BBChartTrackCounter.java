package com.m14n.billib.data;

import com.m14n.billib.data.model.BBChart;
import com.m14n.billib.data.model.BBChartMetadata;
import com.m14n.billib.data.model.BBJournalMetadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BBChartTrackCounter {
    public static void main(String... args) throws IOException {
        final Gson theGson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        final File theMetadataFile = new File(BB.DATA_ROOT + File.separator + "metadata_billboard.json");
        BBJournalMetadata theMetadata = theGson.fromJson(new FileReader(theMetadataFile), BBJournalMetadata.class);

        for (BBChartMetadata theChartMetadata : theMetadata.getCharts()) {
            System.out.println(
                    theChartMetadata.getName() + " " + countBBChartTracks(theGson, theChartMetadata) + " tracks.");
        }
    }

    public static int countBBChartTracks(Gson gson, BBChartMetadata chartMetadata) {
        File theChartDir = new File(BB.DATA_ROOT + File.separator + chartMetadata.getFolder());
        int theResult = 0;
        for (File theChartFile : theChartDir.listFiles()) {
            BBChart theChart = null;
            try {
                theChart = gson.fromJson(new FileReader(theChartFile), BBChart.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            theResult += theChart == null ? 0 : theChart.getTracks().size();
        }
        return theResult;
    }
}
