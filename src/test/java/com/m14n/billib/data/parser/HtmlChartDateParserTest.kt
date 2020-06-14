package com.m14n.billib.data.parser

import CompositeHtmlChartDateParser
import LogOnErrorChartDateParser
import defaultDateParser
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.logging.Logger

class HtmlChartDateParserTest {

    @Test
    fun `default parser with no arguments is CompositeHtmlChartDateParser`() {
        assertTrue(defaultDateParser() is CompositeHtmlChartDateParser)
    }

    @Test
    fun `for null logger default parser is CompositeHtmlChartDateParser`() {
        assertTrue(defaultDateParser(null) is CompositeHtmlChartDateParser)
    }

    @Test
    fun `for non-null logger default parser is LogOnErrorChartDateParser`() {
        assertTrue(defaultDateParser(Logger.getGlobal()) is LogOnErrorChartDateParser)
    }
}
