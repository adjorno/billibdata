package com.m14n.billib.data.parser

import DelegateHtmlChartDateParser
import HtmlChartTextDateParser
import com.m14n.billib.data.BB
import com.m14n.billib.data.html.jsoup.requestAttr
import com.m14n.billib.data.html.jsoup.requestElementById
import com.m14n.billib.data.model.BBPositionInfo
import com.m14n.billib.data.model.BBTrack
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.jsoup.nodes.Document

/**
 * As of June 2020 Billboard has different types of html structures for charts.
 * This parser helps to parse date for Hot-100 html type. Samples can be found
 * in the test resource folder.
 */
fun hot100DateParser() =
    DelegateHtmlChartDateParser(
        Hot100TextDateParser(),
        DateFormatParser(BB.CHART_DATE_FORMAT)
    )

class Hot100TextDateParser : HtmlChartTextDateParser {
    override fun parse(document: Document): String = document.body()
        .requestElementById("main")
        .requestElementById("charts")
        .requestAttr("data-chart-date")
}

@UnstableDefault
class Hot100ChartListParser : HtmlChartListParser {
    override fun parse(document: Document): List<BBTrack> {
        val json = document.body()
            .requestElementById("main")
            .requestElementById("charts")
            .requestAttr("data-charts")

        return Json(
            JsonConfiguration(
                ignoreUnknownKeys = true,
                isLenient = true
            )
        ).parse(
            ListSerializer(Hot100Track.serializer()),
            json
        ).map { hot100Track ->
            BBTrack(
                title = hot100Track.title,
                artist = hot100Track.artist,
                rank = hot100Track.rank.toInt(),
                positionInfo = BBPositionInfo(
                    lastWeek = hot100Track.history.lastWeak,
                    peekPosition = hot100Track.history.peakRank.toInt(),
                    wksOnChart = hot100Track.history.weeksOnChart.toInt()
                )
            )
        }
    }
}

@Serializable
data class Hot100Track(
    @SerialName("artist_name") var artist: String,
    @SerialName("history") var history: Hot100History,
    @SerialName("title") var title: String,
    @SerialName("rank") var rank: String
)

@Serializable
data class Hot100History(
    @SerialName("peak_rank") var peakRank: String,
    @SerialName("last_week") var lastWeak: String?,
    @SerialName("weeks_on_chart") var weeksOnChart: String
)
