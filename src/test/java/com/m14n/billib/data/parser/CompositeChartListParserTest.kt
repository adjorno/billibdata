package com.m14n.billib.data.parser

import com.m14n.billib.data.model.BBTrack
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.text.ParseException

@RunWith(MockitoJUnitRunner::class)
class CompositeChartListParserTest {

    @Mock
    lateinit var delegate1: HtmlChartListParser

    @Mock
    lateinit var delegate2: HtmlChartListParser

    private lateinit var sut: CompositeChartListParser

    @Before
    fun setUp() {
        sut = CompositeChartListParser(listOf(delegate1, delegate2))
    }

    @Test
    fun `if one delegate succeeds there is no need to try following ones`() {
        val result = mock<List<BBTrack>> {}
        whenever(delegate1.parse(any())).doReturn(result)
        assertEquals(result, sut.parse(mock {}))
        verify(delegate2, never()).parse(any())
    }

    @Test
    fun `if one delegate fails try the next one`() {
        whenever(delegate1.parse(any())).doThrow(mock<ParseException> {})
        val result = mock<List<BBTrack>> {}
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
