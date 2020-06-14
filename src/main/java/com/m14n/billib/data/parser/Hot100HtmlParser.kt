package com.m14n.billib.data.parser

import com.m14n.billib.data.BB
import com.m14n.billib.data.html.jsoup.requestAttr
import com.m14n.billib.data.html.jsoup.requestElementById
import org.jsoup.nodes.Document
import java.util.*

/**
 * As of June 2020 Billboard has different types of html structures for charts.
 * This parser helps to parse date for Hot-100 html type. Samples can be found
 * in the test resource folder.
 */
class Hot100DateParser : HtmlChartDateParser {
    override fun parse(document: Document): Date = document.body().requestElementById("main").requestElementById("charts").requestAttr("data-chart-date").let { textDate ->
        BB.CHART_DATE_FORMAT.parse(textDate)
    }
}