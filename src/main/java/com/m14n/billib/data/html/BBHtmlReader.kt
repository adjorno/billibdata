package com.m14n.billib.data.html

import com.m14n.billib.data.BB
import com.m14n.billib.data.model.BBChart
import com.m14n.billib.data.model.BBChartMetadata
import com.m14n.billib.data.model.BBJournalMetadata
import com.m14n.ex.BenchmarkCore
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.stringify

import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.Date

@UseExperimental(UnstableDefault::class, ImplicitReflectionSerializer::class)
object BBHtmlReader {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val theMetadataFile = File(BB.DATA_ROOT + File.separator + "metadata_billboard.json")
        val theMetadata = Json.parse(BBJournalMetadata.serializer(), theMetadataFile.readText())

        var theWeekFolder: File? = null
        var theWeekDate: Date? = null
        theMetadata.charts?.forEach {
            val theBenchmark = BenchmarkCore.start(it.folder)
            val theDocument = BBHtmlParser.getChartDocument(theMetadata, it, null)
            if (theWeekDate == null) {
                theWeekDate = BBHtmlParser.getChartDate(theDocument)
                theWeekFolder = File(BB.DATA_ROOT + File.separator + "week-" + BB.CHART_DATE_FORMAT.format(theWeekDate))
                theWeekFolder?.mkdirs()
            }
            val theChart = BBChart(name = it.name,
                    date = BB.CHART_DATE_FORMAT.format(theWeekDate),
                    tracks = BBHtmlParser.getTracks(theDocument))
            BenchmarkCore.stop(theBenchmark)
            writeChartToFile(theChart, theWeekFolder!!,
                    it.prefix + "-" + theWeekDate + ".json")
        }
    }

    @Throws(IOException::class)
    fun readChart(journalMetadata: BBJournalMetadata, chartMetadata: BBChartMetadata, week: String): BBChart {
        return BBChart(name = chartMetadata.name, date = week,
                tracks = BBHtmlParser.getTracks(BBHtmlParser.getChartDocument(journalMetadata, chartMetadata, week)))
    }

    @Throws(IOException::class)
    fun writeChartToFile(chart: BBChart, folder: File, fileName: String) {
        val theChartFile = File(folder, fileName)
        FileWriter(theChartFile).use {
            it.write(Json.stringify(chart))
        }
    }
}
