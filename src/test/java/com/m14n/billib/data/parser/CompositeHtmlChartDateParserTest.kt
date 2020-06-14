package com.m14n.billib.data.parser

import CompositeHtmlChartDateParser
import HtmlChartDateParser
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.text.ParseException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class CompositeHtmlChartDateParserTest {

    @Mock
    lateinit var delegate1: HtmlChartDateParser

    @Mock
    lateinit var delegate2: HtmlChartDateParser

    private lateinit var sut: CompositeHtmlChartDateParser

    @Before
    fun setUp() {
        sut = CompositeHtmlChartDateParser(listOf(delegate1, delegate2))
    }

    @Test
    fun `if one delegate succeeds there is no need to try following ones`() {
        val result = mock<Date> {}
        whenever(delegate1.parse(any())).doReturn(result)
        assertEquals(result, sut.parse(mock {}))
        verify(delegate2, never()).parse(any())
    }

    @Test
    fun `if one delegate fails try the next one`() {
        whenever(delegate1.parse(any())).doThrow(mock<ParseException> {})
        val result = mock<Date> {}
        whenever(delegate2.parse(any())).doReturn(result)
        assertEquals(result, sut.parse(mock {}))
    }

    @Test(expected = ParseException::class)
    fun `if all delegates fail throw an exception`() {
        whenever(delegate1.parse(any())).doThrow(mock<ParseException> {})
        whenever(delegate2.parse(any())).doThrow(mock<ParseException> {})
        sut.parse(mock {})
    }
}
