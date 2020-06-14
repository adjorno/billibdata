package com.m14n.billib.data.html

import com.m14n.billib.data.BB
import com.m14n.billib.data.model.BBChart
import com.m14n.billib.data.model.BBTrack
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileReader
import java.util.*

fun main() {
    val theOldRoot = File(BB.DATA_ROOT + File.separator + "country-songs")
    val theNewRoot = File(BB.DATA_ROOT + "_new" + File.separator + "country-songs")

    val theCalendar = Calendar.getInstance()
    theCalendar.time = BB.CHART_DATE_FORMAT.parse("2016-12-10")

    var theStop = 0
    while (theStop < 2) {
        val theFormatDate = BB.CHART_DATE_FORMAT.format(theCalendar.time)
        print("$theFormatDate ")
        val theOldFile = File(theOldRoot, "$theFormatDate.json")
        val theNewFile = File(theNewRoot, "country-$theFormatDate.json")
        if (theOldFile.exists() && theNewFile.exists()) {
            val theOldChart = readOld(theOldFile, theFormatDate)
            val theNewChart = readNew(theNewFile)
            if (theNewChart == theOldChart) {
                println("EQUEALS")
            } else {
                println("DIFFERENT!!!")
                break
            }
        } else {
            println("NO FILES!")
            theStop++
        }
        theCalendar.add(Calendar.DATE, -7)
    }
}

private fun readOld(oldChart: File, date: String): BBChart {
    return BBChart(name = "Country", date = date, tracks = FileReader(oldChart).use {
        Json.parse(
            ListSerializer(BBTrack.serializer()),
            oldChart.readText()
        )
    })
}

private fun readNew(newChart: File): BBChart {
    return Json.parse(BBChart.serializer(), newChart.readText())
}
