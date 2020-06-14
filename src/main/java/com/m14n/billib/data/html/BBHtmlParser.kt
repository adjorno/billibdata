package com.m14n.billib.data.html

import com.m14n.billib.data.BB
import com.m14n.billib.data.model.BBChartMetadata
import com.m14n.billib.data.model.BBJournalMetadata
import com.m14n.billib.data.model.BBPositionInfo
import com.m14n.billib.data.model.BBTrack
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.IOException
import java.text.ParseException
import java.util.*

object BBHtmlParser {

    @Throws(IOException::class)
    fun getChartDocument(metadata: BBJournalMetadata, chart: BBChartMetadata, date: String? = null): Document {
        var theUrl = metadata.url + chart.folder
        date?.let {
            if (it.isNotEmpty()) {
                theUrl += "/$date"
            }
        }
        return Jsoup.connect(theUrl).userAgent("Mozilla").timeout(60 * 1000).get()
    }

    private fun getHeader(document: Document): Element {
        val theMain = document.body().getElementById("main")
        return theMain.getElementsByClass("chart-detail-header").first()
    }

    @Throws(ParseException::class)
    fun getChartDate(document: Document): Date {
        val theHeader = getHeader(document)
        val theContainer = theHeader.getElementsByClass("chart-detail-header__chart-info").first()
        val theDetails = theContainer.getElementsByClass("chart-detail-header__select-date").first()
        val theDropdown = theDetails.getElementsByClass("chart-detail-header__date-selector").first()
        val theButton = theDropdown.getElementsByClass("chart-detail-header__date-selector-button").first()
        return BB.CHART_DATE_HTML_FORMAT.parse(theButton.text())
    }

    fun getTracks(document: Document): List<BBTrack> {
        val theTracks = ArrayList<BBTrack>()
        //theTracks.add(getLeader(getHeader(document)))

        val itemLists = document.body()
                .getElementsByClass("chart-container").first()
                .getElementsByClass("chart-details").first()
                .getElementsByClass("chart-list")
        for (list in itemLists) {
            val items = list.getElementsByClass("chart-list-item")
            for (item in items) {
                theTracks.add(getTrack(item))
            }
        }
        return theTracks
    }

    private fun getLeader(header: Element): BBTrack {
        val theLeaderInfo = header.getElementsByClass("chart-number-one").first()
                .getElementsByClass("chart-number-one__info").first()
        val theDetails = theLeaderInfo.getElementsByClass("chart-number-one__details").first()
        val theStats = theLeaderInfo.getElementsByClass("chart-number-one__stats").first()
        return BBTrack(rank = 1,
                title = theDetails.getElementsByClass("chart-number-one__title").first().text(),
                artist = theDetails.getElementsByClass("chart-number-one__artist").first().text(),
                positionInfo = BBPositionInfo(
                        lastWeek = theStats.getElementsByClass("chart-number-one__last-week").first()?.text(),
                        peekPosition = 1,
                        wksOnChart = Integer.valueOf(theStats.getElementsByClass("chart-number-one__weeks-on-chart").first().text())))
    }

    private fun getTrack(row: Element): BBTrack {
        val theExtraInfo = row.getElementsByClass("chart-list-item__extra-info").first()
        return BBTrack(rank = Integer.valueOf(row.attr("data-rank")),
                title = row.attr("data-title"),
                artist = row.attr("data-artist"),
                positionInfo = theExtraInfo?.getElementsByClass("chart-list-item__stats")?.first()?.let {
                    BBPositionInfo(lastWeek = it.getElementsByClass("chart-list-item__last-week").first().text(),
                            peekPosition = Integer.valueOf(it.getElementsByClass("chart-list-item__weeks-at-one").first().text()),
                            wksOnChart = Integer.valueOf(it.getElementsByClass("chart-list-item__weeks-on-chart").first().text()))
                }
        )
    }
}
