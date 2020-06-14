package com.m14n.billib.data.parser

import com.m14n.billib.data.html.jsoup.requestElementById
import com.m14n.billib.data.html.jsoup.requestElementsByClass
import com.m14n.billib.data.html.jsoup.requestElementsByTag
import com.m14n.billib.data.model.BBPositionInfo
import com.m14n.billib.data.model.BBTrack
import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Test

class CountryTrackParserTest {

    private val sut = CountryTrackParser()

    @Test
    fun `should parse correctly`() {
        val document = Jsoup.parse(
            javaClass.classLoader.getResourceAsStream("samples/2020-06-13/country_track.html"),
            "UTF-8",
            "test"
        )
        val trackElement = document
            .requestElementById("main")
            .requestElementsByClass("print-chart").first()
            .requestElementsByTag("table").first()
            .requestElementsByTag("tbody").first()
            .children().first()
        val expectedTrack = BBTrack(
            rank = 48,
            title = "I Won't Let Go",
            artist = "Kirk Jay",
            positionInfo = BBPositionInfo(
                lastWeek = "--",
                peekPosition = 48,
                wksOnChart = 1
            )
        )
        assertEquals(expectedTrack, sut.parse(trackElement))
    }
}