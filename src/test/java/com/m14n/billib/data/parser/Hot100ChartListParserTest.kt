package com.m14n.billib.data.parser

import com.m14n.billib.data.model.BBTrack
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Test

class Hot100ChartListParserTest {

    private val sut = Hot100ChartListParser()

    @Test
    fun `should parse correctly`() {
        val document = Jsoup.parse(
            javaClass.classLoader.getResourceAsStream("samples/2020-06-13/hot_100.html"),
            "UTF-8",
            "test"
        )
        val expectedTracks = javaClass.classLoader
            .getResourceAsStream("samples/2020-06-13/hot_100_tracks.json")
            .bufferedReader().use {
                Json.parse(ListSerializer(BBTrack.serializer()), it.readText())
            }
        assertEquals(expectedTracks, sut.parse(document))
    }
}