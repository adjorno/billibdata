package com.m14n.billib.data.html

import com.m14n.billib.data.BB
import com.m14n.billib.data.model.BBJournalMetadata
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json

import java.io.File

var DATE = "2017-09-30"
var CHART = 0

@UseExperimental(UnstableDefault::class)
fun main() {
    val theRoot = File(BB.DATA_ROOT)
    val theMetadata = Json.parse(BBJournalMetadata.serializer(), File(theRoot, "metadata_billboard.json").readText())

    val theChartMetadata = theMetadata.charts!![CHART]

    val theLastChartDocument = BBHtmlParser.getChartDocument(theMetadata, theChartMetadata, null)
    val theLastWeek = BBHtmlParser.getChartDate(theLastChartDocument)
    val chart = BBHtmlReader.readChart(theMetadata, theChartMetadata, BB.CHART_DATE_FORMAT.format(theLastWeek))

    chart.tracks?.forEach {
        println(it)
    }
}
