package com.m14n.billib.data.parser

import com.m14n.billib.data.model.BBChart
import com.m14n.billib.data.model.BBTrack
import org.jsoup.nodes.Document
import java.text.ParseException
import java.util.*

interface HtmlChartParser {
    @Throws(ParseException::class)
    fun parse(document: Document): BBChart?
}

interface HtmlChartDateParser {
    @Throws(ParseException::class)
    fun parse(document: Document): Date
}

interface HtmlChartListParser {
    @Throws(ParseException::class)
    fun parse(document: Document): List<BBTrack>
}