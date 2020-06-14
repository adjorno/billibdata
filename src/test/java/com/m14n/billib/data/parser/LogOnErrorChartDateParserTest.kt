package com.m14n.billib.data.parser

import HtmlChartDateParser
import LogOnErrorChartDateParser
import com.nhaarman.mockitokotlin2.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.text.ParseException
import java.util.*
import java.util.logging.Handler
import java.util.logging.Logger


@RunWith(MockitoJUnitRunner::class)
class LogOnErrorChartDateParserTest {

    @Mock
    lateinit var delegate: HtmlChartDateParser

    @Mock
    lateinit var handler: Handler

    lateinit var logger: Logger

    private lateinit var sut: LogOnErrorChartDateParser

    @Before
    fun setUp() {
        logger = Logger.getLogger("test").apply {
            addHandler(handler)
        }
        sut = LogOnErrorChartDateParser(delegate, logger)
    }

    @Test
    fun `successful parsing operation should not log`() {
        val result = mock<Date> {}
        whenever(delegate.parse(any())).doReturn(result)

        val doc = mock<Document> {}
        assertEquals(result, sut.parse(doc))

        verify(handler, never()).publish(any())
    }

    @Test
    fun `failed parsing operation should log the html document`() {
        val parseException = mock<ParseException> {}
        whenever(delegate.parse(any())).doThrow(parseException)

        val htmlDoc = """<html class="" lang=""><head></head><body></body></html>"""
        val doc = Jsoup.parse(htmlDoc)

        try {
            sut.parse(doc)
        } catch (e: ParseException) {
            assertEquals(parseException, e)
        }

        verify(handler, times(1)).publish(
            argThat {
                message.replace("\\s".toRegex(), "") ==
                        htmlDoc.replace("\\s".toRegex(), "")
            }
        )
    }
}
