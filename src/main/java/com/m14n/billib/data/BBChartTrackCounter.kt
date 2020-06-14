package com.m14n.billib.data

import com.m14n.billib.data.model.BBChart
import com.m14n.billib.data.model.BBChartMetadata
import com.m14n.billib.data.model.BBJournalMetadata
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json

import java.io.File

@UseExperimental(UnstableDefault::class)
fun main() {
    val theMetadataFile = File(BB.DATA_ROOT + File.separator + "metadata_billboard.json")
    val theMetadata = Json.parse(BBJournalMetadata.serializer(), theMetadataFile.readText())

    theMetadata.charts?.forEach {
        println(it.name + " " + countBBChartTracks(it) + " tracks.")
    }
}

@UseExperimental(UnstableDefault::class)
fun countBBChartTracks(chartMetadata: BBChartMetadata): Int {
    val theChartDir = File(BB.DATA_ROOT + File.separator + chartMetadata.folder)
    var theResult = 0
    for (theChartFile in theChartDir.listFiles()!!) {
        val theChart = Json.parse(BBChart.serializer(), theChartFile.readText())
        theResult += theChart.tracks?.size ?: 0
    }
    return theResult
}
