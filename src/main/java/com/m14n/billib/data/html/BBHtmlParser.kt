package com.m14n.billib.data.html

import com.m14n.billib.data.model.BBChartMetadata
import com.m14n.billib.data.model.BBJournalMetadata
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

object BBHtmlParser {

    @Throws(IOException::class)
    fun getChartDocument(metadata: BBJournalMetadata, chart: BBChartMetadata, date: String? = null): Document {
        var theUrl = metadata.url + chart.folder
        date?.let {
            if (it.isNotEmpty()) {
                theUrl += "/$date"
            }
        }
        return Jsoup.connect(theUrl)
            .userAgent("Mozilla")
            .maxBodySize(1024 * 1024 * 10)
            .timeout(60 * 1000)
            .get()
    }
}
