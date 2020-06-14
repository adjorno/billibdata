package com.m14n.billib.data.parser

import DelegateHtmlChartDateParser
import HtmlChartDateParser
import HtmlChartTextDateParser
import com.m14n.billib.data.BB
import com.m14n.billib.data.html.jsoup.requestElementById
import com.m14n.billib.data.html.jsoup.requestElementsByClass
import org.jsoup.nodes.Document

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
        .requestElementsByClass("print-chart__week")
        .first().textNodes().first().text().trim()
        .substring("This week of ".length - 1)
}
