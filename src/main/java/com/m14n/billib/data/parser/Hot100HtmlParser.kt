package com.m14n.billib.data.parser

import com.m14n.billib.data.BB
import org.jsoup.nodes.Document
import java.util.*

/**
 * As of June 2020 Billboard has different types of html structures for charts.
 * This parser helps to parse date for Hot-100 html type. Samples can be found
 * in the test resource folder.
 */
class Hot100DateParser : HtmlChartDateParser {
    override fun parse(document: Document): Date {
        val main = document.body().getElementById("main")
        val charts = main.getElementById("charts")
        return BB.CHART_DATE_FORMAT.parse(charts.attr("data-chart-date"))
    }
}