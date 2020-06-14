package com.m14n.billib.data.parser

import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class CountryHtmlParserIntegrationTest {
    private val sut = countryDateParser()

    @Test
    fun `should parse the date correctly`() {
        val doc = Jsoup.parse(
            javaClass.classLoader.getResourceAsStream("samples/2020-06-13/country_2018_12_29.html"),
            "UTF-8",
            "test"
        )
        val date = sut.parse(doc)
        val calendar = Calendar.getInstance().apply {
            time = date
        }
        // 2018 December, 29
        assertEquals(2018, calendar.get(Calendar.YEAR))
        assertEquals(11, calendar.get(Calendar.MONTH))
        assertEquals(29, calendar.get(Calendar.DAY_OF_MONTH))
    }
}
