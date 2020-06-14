package com.m14n.billib.data.html

import com.m14n.billib.data.BB
import com.m14n.billib.data.model.BBChart
import com.m14n.billib.data.model.BBChartMetadata
import com.m14n.billib.data.model.BBJournalMetadata
import com.m14n.billib.data.model.BBTrack
import com.m14n.billib.data.parser.defaultChartListParser
import defaultDateParser
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.stringify
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.ParseException
import java.util.*
import java.util.logging.FileHandler
import java.util.logging.Logger

@UseExperimental(ImplicitReflectionSerializer::class, UnstableDefault::class)
object AllBBReader {
    private const val POSSIBLE_SKIPPED_WEEKS_IN_ROW = 5
    private var TODAY = BB.CHART_DATE_FORMAT.parse("2020-06-20")

    @JvmStatic
    fun main(args: Array<String>) {
        val theRoot = File(BB.DATA_ROOT)
        val theMetadataFile = File(theRoot, "metadata_billboard.json")
        val theMetadata = Json.parse(BBJournalMetadata.serializer(), theMetadataFile.readText())

        theMetadata.charts?.forEach {
            fetchChart(theRoot, theMetadata, it)
        }
    }

    @Throws(ParseException::class)
    private fun fetchChart(root: File, metadata: BBJournalMetadata, theChartMetadata: BBChartMetadata) {
        println("---------------------" + theChartMetadata.name + "----------------------------")
        val theChartDir = File(root, theChartMetadata.folder)
        if (!theChartDir.exists()) {
            theChartDir.mkdirs()
        }
        var theSkip = 0
        val theCalendar = Calendar.getInstance()
        theCalendar.time = TODAY!!
        val dateParser = defaultDateParser(Logger.getLogger("ChartUpdate").apply {
            addHandler(FileHandler("charts_update.log"))
        })
        val tracksParser = defaultChartListParser()
        while (theSkip <= POSSIBLE_SKIPPED_WEEKS_IN_ROW) {
            val theCurrent = BB.CHART_DATE_FORMAT.format(theCalendar.time)
            theCalendar.add(
                Calendar.DATE,
                if ("2018-01-06" == theCurrent) -3 else if ("2018-01-03" == theCurrent) -4 else -7
            )
            val theFormatDate = BB.CHART_DATE_FORMAT.format(theCalendar.time)
            print(theChartMetadata.name + " " + theFormatDate + " ")
            val theChartFile = File(
                theChartDir,
                theChartMetadata.prefix + "-" + theFormatDate + ".json"
            )
            if (!theChartFile.exists()) {
                Thread.sleep(2000)
                try {
                    val theChartDocument = BBHtmlParser.getChartDocument(
                        metadata, theChartMetadata,
                        theFormatDate
                    )
                    val theHtmlDate = BB.CHART_DATE_FORMAT.format(dateParser.parse(theChartDocument))
                    if (theFormatDate != theHtmlDate
                        // Billboard mistake as always :-)
                        && !("2018-11-10" == theHtmlDate && "2018-11-03" == theFormatDate &&
                                "Youtube" == theChartMetadata.name)
                    ) {
                        println("WRONG DATE!")
                        theSkip++
                        continue
                    }
                    var theTracks: List<BBTrack>?
                    try {
                        theTracks = tracksParser.parse(theChartDocument)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        theSkip++
                        continue
                    }

                    if (theTracks.size != theChartMetadata.size) {
                        print("SIZE = " + theTracks.size + " EXPECTED = " + theChartMetadata.size + " ")
                    }
                    val theChart = BBChart(
                        name = theChartMetadata.name,
                        date = theFormatDate, tracks = theTracks
                    )

                    FileWriter(theChartFile).use {
                        it.write(Json(JsonConfiguration(prettyPrint = true)).stringify(theChart))
                    }
                    println("SUCCESS!")
                    theSkip = 0
                } catch (e: IOException) {
                    e.printStackTrace()
                    theSkip++
                }

            } else {
                theSkip = 0
                println("ALREADY EXISTS")
            }
        }
    }
}
