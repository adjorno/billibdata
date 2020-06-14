package com.m14n.billib.data.parser

import DelegateHtmlChartDateParser
import HtmlChartDateParser
import HtmlChartTextDateParser
import com.m14n.billib.data.BB
import com.m14n.billib.data.html.jsoup.nodeText
import com.m14n.billib.data.html.jsoup.requestElementById
import com.m14n.billib.data.html.jsoup.requestElementsByClass
import com.m14n.billib.data.html.jsoup.requestElementsByTag
import com.m14n.billib.data.model.BBPositionInfo
import com.m14n.billib.data.model.BBTrack
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

/**
 * As of June 2020 Billboard has different types of html structures for charts.
 * This parser helps to parse date for Country html type. Samples can be found
 * in the test resource folder
 */
fun countryDateParser(): HtmlChartDateParser =
    DelegateHtmlChartDateParser(
        CountryHtmlTextDateParser(),
        DateFormatParser(BB.CHART_DATE_HTML_FORMAT)
    )

class CountryHtmlTextDateParser : HtmlChartTextDateParser {
    override fun parse(document: Document): String = document
        .requestElementById("main")
        .requestElementsByClass("print-chart").first()
        .requestElementsByClass("print-chart__week").first()
        .nodeText()
        .substring("This week of ".length - 1)
}

class CountryTrackElementsParser : HtmlTrackElementsParser {
    override fun parse(document: Document): Sequence<Element> = document
        .requestElementById("main")
        .requestElementsByClass("print-chart").first()
        .requestElementsByTag("table").first()
        .requestElementsByTag("tbody").first()
        .children().asSequence()
}

class CountryTrackParser : TrackElementParser {
    override fun parse(element: Element): BBTrack {
        val itemDetails = element.requestElementsByClass("item-details").first()
        val title = itemDetails
            .requestElementsByClass("item-details__title")
            .first()
            .nodeText()
        val artist = itemDetails
            .requestElementsByClass("item-details__artist")
            .first()
            .nodeText()
        val positionDetails = element.requestElementsByTag("td")
        val lastWeek = positionDetails[1].nodeText()
        val rank = positionDetails[2].nodeText().toInt()
        val peekPosition = try {
            positionDetails[0].nodeText().toInt()
        } catch (e: NumberFormatException) {
            rank
        }
        val wksOnChart = positionDetails[6].nodeText().toInt()
        return BBTrack(rank, title, artist, BBPositionInfo(lastWeek, peekPosition, wksOnChart))
    }
}