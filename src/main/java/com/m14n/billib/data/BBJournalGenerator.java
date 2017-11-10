package com.m14n.billib.data;

import com.m14n.billib.data.model.BBChartMetadata;
import com.m14n.billib.data.model.BBJournalMetadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.m14n.ex.Ex;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BBJournalGenerator {
    private static final String JOURNAL_NAME = "Billboard";
    private static final String[] CHART_NAME =
            new String[] {"Hot 100", "Country", "Club", "Electronic", "Pop", "R&B", "Hip-Hop", "Rap", "Rock", "Youtube",
                    "Latin", "Germany", "France", "Alternative", "UK", "Canada", "Japan", "Christian", "Gospel",
                    "Rhytmic"};
    private static final String[] CHART_FOLDER =
            new String[] {"hot-100", "country-songs", "dance-club-play-songs", "dance-electronic-songs", "pop-songs",
                    "r-and-b-songs", "r-b-hip-hop-songs", "rap-song", "rock-songs", "youtube", "latin-songs",
                    "germany-songs", "france-digital-song-sales", "alternative-songs", "official-uk-albums",
                    "canadian-hot-100", "japan-hot-100", "christian-songs", "gospel-songs", "rhythmic-40"};
    private static final int[] CHART_SIZE =
            new int[] {100, 50, 50, 50, 40, 25, 50, 25, 50, 25, 50, 10, 10, 40, 20, 100, 100, 50, 25, 40};
    private static final String[] CHART_PREFIX =
            new String[] {"hot100", "country", "club", "electro", "pop", "rnb", "hiphop", "rap", "rock", "youtube",
                    "latin", "germany-songs", "france", "alternative", "uk", "canada", "japan", "christian", "gospel",
                    "rhytmic"};
    private static final String[] CHART_START_DATE =
            new String[] {"1958-08-09", "1962-01-06", "1985-01-05" /*1976-08-28*/, "2013-01-26", "1992-10-03", "2012-10-20",
                    "1962-01-06", "1989-03-11", "2009-06-20", "2011-08-13", "1986-09-06", "2011-05-21", "2007-09-08",
                    "1988-09-10", "2011-01-29", "2007-03-31", "2011-04-09", "2003-06-21", "2005-03-19", "1992-10-03"};

    public static void main(String... args) throws Exception {
        final List<BBChartMetadata> theCharts = new ArrayList<>();
        for (int i = 0; i < CHART_NAME.length; i++) {
            final BBChartMetadata theChart = new BBChartMetadata();
            theChart.setName(CHART_NAME[i]);
            theChart.setFolder(CHART_FOLDER[i]);
            theChart.setSize(CHART_SIZE[i]);
            theChart.setPrefix(CHART_PREFIX[i]);
            theChart.setStartDate(CHART_START_DATE[i]);
            theCharts.add(theChart);
        }
        Collections.sort(theCharts, new Comparator<BBChartMetadata>() {
            @Override
            public int compare(BBChartMetadata o1, BBChartMetadata o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });
        final BBJournalMetadata theJournal = new BBJournalMetadata();
        theJournal.setName(JOURNAL_NAME);
        theJournal.setUrl("http://www.billboard.com/charts/");
        theJournal.setBaseRss("http://www.billboard.com/rss/charts/");
        theJournal.setCharts(theCharts);

        final File theOutFile = new File("metadata_billboard.json");
        final Gson theGson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        final FileWriter theFileWriter = new FileWriter(BB.DATA_ROOT + File.separator + theOutFile);
        theGson.toJson(theJournal, theFileWriter);
        Ex.closeSilently(theFileWriter);
    }
}
