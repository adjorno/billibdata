package com.m14n.billib.data.parser

import HtmlChartDateParser
import org.jsoup.nodes.Document
import java.util.*

class ThrowExceptionParser:HtmlChartDateParser {
    override fun parse(document: Document): Date {
        throw IllegalStateException("This method should not called")
    }
}