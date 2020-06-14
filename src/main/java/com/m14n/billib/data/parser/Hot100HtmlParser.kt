package com.m14n.billib.data.parser

import DelegateHtmlChartDateParser
import HtmlChartTextDateParser
import com.m14n.billib.data.BB
import com.m14n.billib.data.html.jsoup.requestAttr
import com.m14n.billib.data.html.jsoup.requestElementById
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
