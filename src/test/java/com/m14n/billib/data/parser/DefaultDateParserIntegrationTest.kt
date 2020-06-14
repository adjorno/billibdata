package com.m14n.billib.data.parser

import HtmlChartDateParser
import com.m14n.billib.data.BB
import com.m14n.billib.data.html.BBHtmlParser
import com.m14n.billib.data.model.BBJournalMetadata
import defaultDateParser
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.logging.FileHandler
import java.util.logging.Logger

class DefaultDateParserIntegrationTest {

    private lateinit var sut: HtmlChartDateParser

    @Before
    fun setUp() {
        sut = defaultDateParser(Logger.getLogger("DefaultDateParserIntegrationTest").apply {
            addHandler(FileHandler("failed_html_charts_to_parse_date.log").apply {
                encoding = "UTF-8"
            })
        })
    }

    @Test
    fun `default parser should parse hot 100 latest chart`() {
        val theRoot = File(BB.DATA_ROOT)
        val theMetadata =
            Json.parse(BBJournalMetadata.serializer(), File(theRoot, "metadata_billboard.json").readText())
        theMetadata.charts?.first { chart -> chart.name == "Hot 100" }?.let { chartMeta ->
            val doc = BBHtmlParser.getChartDocument(theMetadata, chartMeta)
            sut.parse(doc)
        }
    }

    @Test
    fun `default parser should parse all latest charts except hot 100`() {
        val theRoot = File(BB.DATA_ROOT)
        val journal =
            Json.parse(BBJournalMetadata.serializer(), File(theRoot, "metadata_billboard.json").readText())
        journal.charts?.asSequence()?.filter { it.name != "Hot 100" }?.forEach { chart ->
            if (chart.name != "Hot 100") {
                val doc = BBHtmlParser.getChartDocument(journal, chart)
                sut.parse(doc)
            }
        }
    }
}
