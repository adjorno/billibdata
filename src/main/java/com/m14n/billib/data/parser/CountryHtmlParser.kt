package com.m14n.billib.data.parser

import org.jsoup.nodes.Document
import java.util.*

/**
 * As of June 2020 Billboard has different types of html structures for charts.
 * This parser helps to parse date for Country html type. Samples can be found
 * in the test resource folder
 */
class CountryDateParser : HtmlChartDateParser {
    override fun parse(document: Document): Date {
        val main = document.body().getElementById("main")
        throw RuntimeException()
    }
}