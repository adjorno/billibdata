package com.m14n.billib.data.parser

import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class Hot100DateParserTest {

    private val sut = Hot100DateParser()

    @Test
    fun `should parse the date correctly`() {
        val doc = Jsoup.parse(
            javaClass.classLoader.getResourceAsStream("samples/2020-06-13/hot100.html"),
            "UTF-8",
            "test"
        )
        val date = sut.parse(doc)
        val calendar = Calendar.getInstance().apply {
            time = date
        }
        // 2020 June, 13
        assertEquals(2020, calendar.get(Calendar.YEAR))
        assertEquals(5, calendar.get(Calendar.MONTH))
        assertEquals(13, calendar.get(Calendar.DAY_OF_MONTH))
    }
}