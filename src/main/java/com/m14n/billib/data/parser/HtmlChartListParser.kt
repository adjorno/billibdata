package com.m14n.billib.data.parser

import com.m14n.billib.data.model.BBTrack
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.text.ParseException

fun defaultChartListParser(): HtmlChartListParser = DelegateHtmlChartParser(
    CountryTrackElementsParser(),
    CountryTrackParser()
)

interface HtmlChartListParser {
    @Throws(ParseException::class)
    fun parse(document: Document): List<BBTrack>
}

/**
 * Parses [Document] and generates [Sequence] of [Element] with tracks.
 */
interface HtmlTrackElementsParser {
    @Throws(ParseException::class)
    fun parse(document: Document): Sequence<Element>
}

/**
 * Parses html [Element] as a [BBTrack]
 */
interface TrackElementParser {
    @Throws(ParseException::class)
    fun parse(element: Element): BBTrack
}

/**
 * Primitive [HtmlChartListParser] implementation via delegates
 */
class DelegateHtmlChartParser(
    private val htmlTrackElementsParser: HtmlTrackElementsParser,
    private val trackElementParser: TrackElementParser
) : HtmlChartListParser {
    override fun parse(document: Document): List<BBTrack> =
        htmlTrackElementsParser.parse(document).map { trackElement ->
            trackElementParser.parse(trackElement)
        }.toList()
}
