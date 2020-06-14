package com.m14n.billib.data.parser

import com.m14n.billib.data.model.BBChart
import com.m14n.billib.data.model.BBTrack
import org.jsoup.nodes.Document
import java.text.ParseException

interface HtmlChartParser {
    @Throws(ParseException::class)
    fun parse(document: Document): BBChart?
}

interface HtmlChartListParser {
    @Throws(ParseException::class)
    fun parse(document: Document): List<BBTrack>
}
