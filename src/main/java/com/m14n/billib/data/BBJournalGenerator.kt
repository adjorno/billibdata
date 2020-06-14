package com.m14n.billib.data

import com.m14n.billib.data.model.BBChartMetadata
import com.m14n.billib.data.model.BBJournalMetadata
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.stringify

import java.io.File
import java.io.FileWriter
import java.util.ArrayList
import java.util.Comparator

@UseExperimental(ImplicitReflectionSerializer::class)
object BBJournalGenerator {
    private val CHART_NAME = arrayOf("Hot 100", "Country", "Club", "Electronic", "Pop", "R&B", "Hip-Hop", "Rap", "Rock", "Youtube", "Latin", "Germany", "France", "Alternative", "UK", "Canada", "Japan", "Christian", "Gospel", "Rhytmic")
    private val CHART_FOLDER = arrayOf("hot-100", "country-songs", "dance-club-play-songs", "dance-electronic-songs", "pop-songs", "r-and-b-songs", "r-b-hip-hop-songs", "rap-song", "rock-songs", "youtube", "latin-songs", "germany-songs", "france-digital-song-sales", "alternative-songs", "official-uk-albums", "canadian-hot-100", "japan-hot-100", "christian-songs", "gospel-songs", "rhythmic-40")
    private val CHART_SIZE = intArrayOf(100, 50, 50, 50, 40, 25, 50, 25, 50, 25, 50, 10, 10, 40, 20, 100, 100, 50, 25, 40)
    private val CHART_PREFIX = arrayOf("hot100", "country", "club", "electro", "pop", "rnb", "hiphop", "rap", "rock", "youtube", "latin", "germany-songs", "france", "alternative", "uk", "canada", "japan", "christian", "gospel", "rhytmic")
    private val CHART_START_DATE = arrayOf("1958-08-09", "1962-01-06", "1985-01-05" /*1976-08-28*/, "2013-01-26", "1992-10-03", "2012-10-20", "1962-01-06", "1989-03-11", "2009-06-20", "2011-08-13", "1986-09-06", "2011-05-21", "2007-09-08", "1988-09-10", "2011-01-29", "2007-03-31", "2011-04-09", "2003-06-21", "2005-03-19", "1992-10-03")

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val theCharts = ArrayList<BBChartMetadata>()
        for (i in CHART_NAME.indices) {
            val theChart = BBChartMetadata()
            theChart.name = CHART_NAME[i]
            theChart.folder = CHART_FOLDER[i]
            theChart.size = CHART_SIZE[i]
            theChart.prefix = CHART_PREFIX[i]
            theChart.startDate = CHART_START_DATE[i]
            theCharts.add(theChart)
        }
        theCharts.sortWith(Comparator { c1, c2 -> c1.startDate!!.compareTo(c2.startDate!!) })
        val theJournal = BBJournalMetadata(name = "Billboard",
                url = "http://www.billboard.com/charts/",
                baseRss = "http://www.billboard.com/rss/charts/",
                charts = theCharts)

        val theOutFile = File("metadata_billboard.json")
        FileWriter(BB.DATA_ROOT + File.separator + theOutFile).use {
            it.write(Json.stringify(theJournal))
        }
    }
}
