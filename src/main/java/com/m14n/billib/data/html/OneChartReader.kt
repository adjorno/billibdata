package com.m14n.billib.data.html

import com.m14n.billib.data.BB
import com.m14n.billib.data.model.BBJournalMetadata
import com.m14n.billib.data.parser.Hot100DateParser
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import java.io.File
import java.text.ParseException

var DATE = "2018-12-29"
var CHART = 1

@UseExperimental(UnstableDefault::class)
fun main() {
    val theRoot = File(BB.DATA_ROOT)
    val theMetadata = Json.parse(BBJournalMetadata.serializer(), File(theRoot, "metadata_billboard.json").readText())

    val dateParser = Hot100DateParser()
    theMetadata.charts?.forEach { chartMeta ->
        println("OLOLO ${chartMeta.name} STARTED")

        val theLastChartDocument = BBHtmlParser.getChartDocument(theMetadata, chartMeta, DATE)
        try {
            val theLastWeek = dateParser.parse(theLastChartDocument)
            println("OLOLO $theLastWeek")
//            val chart = BBHtmlReader.readChart(theMetadata, chartMeta, BB.CHART_DATE_FORMAT.format(theLastWeek))
//
//            chart.tracks?.forEach {
//                println(it)
//            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        println("OLOLO ${chartMeta.name} FINISHED")
    }
}
