package com.m14n.billib.data.parser

import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Test

class CountryTrackElementsParserTest {

    private val sut = CountryTrackElementsParser()

    @Test
    fun `should parse correctly`() {
        val doc = Jsoup.parse(
            javaClass.classLoader.getResourceAsStream("samples/2020-06-13/country_2018_12_29.html"),
            "UTF-8",
            "test"
        )
        assertEquals(50, sut.parse(doc).toList().size)
    }
}