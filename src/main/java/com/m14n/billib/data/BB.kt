package com.m14n.billib.data

import com.m14n.billib.data.BB.CHART_DATE_FORMAT
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object BB {
    const val DATA_ROOT = "data"

    const val CHART_DATE_HTML_FORMAT_STRING = "MMMM dd, yyyy"
    const val CHART_DATE_FORMAT_STRING = "yyyy-MM-dd"

    val CHART_DATE_HTML_FORMAT: DateFormat = SimpleDateFormat(CHART_DATE_HTML_FORMAT_STRING)
    val CHART_DATE_FORMAT: DateFormat = SimpleDateFormat(CHART_DATE_FORMAT_STRING)
    const val OLD_LAST_WEEK_NEWBIE = "--"
    const val LAST_WEEK_NEWBIE = "-"

    fun extractLastWeekRank(lastWeekStr: String): Int {
        if (LAST_WEEK_NEWBIE != lastWeekStr && OLD_LAST_WEEK_NEWBIE != lastWeekStr) {
            try {
                return Integer.valueOf(lastWeekStr)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }

        }
        return 0
    }
}

fun String.toChartDate(): Date = CHART_DATE_FORMAT.parse(this)
var TODAY = "2020-06-20".toChartDate()