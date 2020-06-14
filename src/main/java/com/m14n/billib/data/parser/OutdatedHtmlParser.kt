package com.m14n.billib.data.parser

import HtmlChartDateParser
import com.m14n.billib.data.BB
import com.m14n.billib.data.model.BBPositionInfo
import com.m14n.billib.data.model.BBTrack
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.text.ParseException
import java.util.*

@Deprecated("defaultDateParser()")
class OutdatedDateParser : HtmlChartDateParser {

    @Throws(ParseException::class)
    override fun parse(document: Document): Date {
        val theMain = document.body().getElementById("main")
        val theHeader = theMain.getElementsByClass("chart-detail-header").first()
        val theContainer = theHeader.getElementsByClass("chart-detail-header__chart-info").first()
        val theDetails = theContainer.getElementsByClass("chart-detail-header__select-date").first()
        val theDropdown = theDetails.getElementsByClass("chart-detail-header__date-selector").first()
        val theButton = theDropdown.getElementsByClass("chart-detail-header__date-selector-button").first()
        return BB.CHART_DATE_HTML_FORMAT.parse(theButton.text())
    }
}

class OutdatedChartListParser : HtmlChartListParser {
    override fun parse(document: Document): List<BBTrack> {
        val theTracks = ArrayList<BBTrack>()

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

    private fun getTrack(row: Element): BBTrack {
        val theExtraInfo = row.getElementsByClass("chart-list-item__extra-info").first()
        return BBTrack(rank = Integer.valueOf(row.attr("data-rank")),
            title = row.attr("data-title"),
            artist = row.attr("data-artist"),
            positionInfo = theExtraInfo?.getElementsByClass("chart-list-item__stats")?.first()?.let {
                BBPositionInfo(
                    lastWeek = it.getElementsByClass("chart-list-item__last-week").first().text(),
                    peekPosition = Integer.valueOf(
                        it.getElementsByClass("chart-list-item__weeks-at-one").first().text()
                    ),
                    wksOnChart = Integer.valueOf(
                        it.getElementsByClass("chart-list-item__weeks-on-chart").first().text()
                    )
                )
            }
        )
    }
}
