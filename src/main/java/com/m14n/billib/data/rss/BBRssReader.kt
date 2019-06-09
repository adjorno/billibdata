package com.m14n.billib.data.rss

import com.m14n.billib.data.BB
import com.m14n.billib.data.model.BBJournalMetadata
import com.m14n.billib.data.model.BBTrack
import com.m14n.ex.BenchmarkCore
import com.m14n.ex.Ex
import com.sun.syndication.feed.WireFeed
import com.sun.syndication.feed.rss.Channel
import com.sun.syndication.io.FeedException
import com.sun.syndication.io.WireFeedInput
import com.sun.syndication.io.XmlReader
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.stringify

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.jdom.Document

import java.io.File
import java.io.FileWriter
import java.net.URL
import java.util.ArrayList

@UseExperimental(UnstableDefault::class, ImplicitReflectionSerializer::class)
fun main() {
    val theMetadataFile = File(BB.DATA_ROOT + File.separator + "metadata_billboard.json")
    val theMetadata = Json.parse(BBJournalMetadata.serializer(), theMetadataFile.readText())

    val theRssInput = object : WireFeedInput() {
        @Throws(IllegalArgumentException::class, FeedException::class)
        override fun build(document: Document): WireFeed {
            return BBRssItemParser().parse(document, false)
        }
    }

    var theWeekFolder: File? = null
    var theWeekDate: String? = null
    val client = HttpClients.custom()
            .setUserAgent("Mozilla")
            .build()
    theMetadata.charts?.forEach {
        val theBenchmark = BenchmarkCore.start(it.folder)
        val theRssSource = URL(theMetadata.baseRss + it.folder)
        val request = HttpGet(theRssSource.toURI())
        val response = client.execute(request)
        val stream = response.entity.content
        val feed = theRssInput.build(XmlReader(stream)) as Channel
        val theItems = feed.items as List<BBRssItem>
        val theTracks = ArrayList<BBTrack>()
        for (theRssItem in theItems) {
            val theTrack = BBTrack(rank = theRssItem.rank, title = theRssItem.title,
                    artist = theRssItem.artist)
            if (Ex.isEmpty(theWeekDate)) {
                theWeekDate = BB.CHART_DATE_FORMAT.format(theRssItem.date)
                theWeekFolder = File(BB.DATA_ROOT + File.separator + "week-" + theWeekDate)
                theWeekFolder?.mkdirs()
            }
            println(theTrack)
            theTracks.add(theTrack)
        }
        BenchmarkCore.stop(theBenchmark)
        val theChartFile = File(theWeekFolder, it.prefix + "-" + theWeekDate + ".json")
        FileWriter(theChartFile).use {
            it.write(Json.stringify(theTracks))
        }
    }
}
