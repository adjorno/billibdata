package com.m14n.billib.data

import com.m14n.ex.Ex

import java.text.DateFormat
import java.text.SimpleDateFormat

object BB {
    const val DATA_ROOT = "data"

    const val CHART_DATE_HTML_FORMAT_STRING = "MMMM dd, yyyy"
    const val CHART_DATE_FORMAT_STRING = "yyyy-MM-dd"

    val CHART_DATE_HTML_FORMAT: DateFormat = SimpleDateFormat(CHART_DATE_HTML_FORMAT_STRING)
    val CHART_DATE_FORMAT: DateFormat = SimpleDateFormat(CHART_DATE_FORMAT_STRING)
    const val OLD_LAST_WEEK_NEWBY = "--"
    const val LAST_WEEK_NEWBY = "-"

    fun extractLastWeekRank(lastWeekStr: String): Int {
        if (LAST_WEEK_NEWBY != lastWeekStr && OLD_LAST_WEEK_NEWBY != lastWeekStr) {
            try {
                return Integer.valueOf(lastWeekStr)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }

        }
        return 0
    }

}
