package com.m14n.billib.data

import com.m14n.billib.data.model.BBChart
import com.m14n.billib.data.model.BBJournalMetadata
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileReader
import java.util.*

private var TODAY = BB.CHART_DATE_FORMAT.parse("2018-07-21")

@UseExperimental(UnstableDefault::class)
fun main() {
    val theMetadataFile = File(BB.DATA_ROOT, "metadata_billboard.json")
    val theMetadata = Json.parse(BBJournalMetadata.serializer(), theMetadataFile.readText())
    val theCalendar = Calendar.getInstance()

    for (theChartMetadata in theMetadata.charts!!) {
        val theChartFolder = File(BB.DATA_ROOT, theChartMetadata.folder!!)
        theCalendar.time = BB.CHART_DATE_FORMAT.parse("2018-06-23")
        var thePreviousChart: BBChart? = null
        while (theCalendar.time <= TODAY) {
            val theDate = BB.CHART_DATE_FORMAT.format(theCalendar.time)
            val theFileName = theChartMetadata.prefix + "-" + theDate + ".json"
            val theFile = File(theChartFolder, theFileName)
            var theChart: BBChart? = null
            if (theFile.exists()) {
                val theReader = FileReader(theFile)
                try {
                    theChart = Json.parse(BBChart.serializer(), theFile.readText())
                    if (thePreviousChart != null) {
                        if (!checkConsistency(thePreviousChart, theChart)) {
                            println(String.format("=========== ERROR ========== %s %s ",
                                    theChartMetadata.name, theDate))
                        }
                    }
                } finally {
                    theReader.close()
                }
            } else {
                println(String.format("%s DOES NOT EXIST!", theFileName))
            }
            thePreviousChart = theChart
            theCalendar.add(Calendar.DATE, 7)
        }
    }
}

private fun checkConsistency(previousChart: BBChart, chart: BBChart): Boolean {
    var theResult = true
    for ((rank, title, artist, thePositionInfo) in chart.tracks!!) {
        val theLastWeek = BB.extractLastWeekRank(thePositionInfo?.lastWeek!!)
        if (theLastWeek > 0 && previousChart.tracks!!.size >= theLastWeek) {
            val (_, title1, artist1) = previousChart.tracks!![theLastWeek - 1]
            if (!(title.toLowerCase() == title1.toLowerCase() && artist!!.toLowerCase() == artist1!!.toLowerCase())) {
                println(String.format("CHECK %d", rank))
                theResult = false
            }
        }
    }
    return theResult
}
