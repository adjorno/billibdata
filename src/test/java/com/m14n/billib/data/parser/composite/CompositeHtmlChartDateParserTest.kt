package com.m14n.billib.data.parser.composite

import CompositeHtmlChartDateParser
import com.m14n.billib.data.parser.ReturnConstantParser
import com.m14n.billib.data.parser.ThrowExceptionParser
import org.amshove.kluent.`should equal`
import org.jsoup.nodes.Document
import org.junit.Test
import java.util.*

class CompositeHtmlChartDateParserTest {
    val emptyDocument = Document("")

    @Test
    fun `if one delegate succeeds there is no need to try following ones`() {
        val date = Date()
        val parser = CompositeHtmlChartDateParser(listOf(ReturnConstantParser(date), ThrowExceptionParser()))
        parser.parse(emptyDocument) `should equal` date
    }
}