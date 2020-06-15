package com.m14n.billib.data.parser

import HtmlChartDateParser
import org.jsoup.nodes.Document
import java.util.*

class ReturnConstantParser(
    private val date: Date
) : HtmlChartDateParser {
    override fun parse(document: Document) = date
}
