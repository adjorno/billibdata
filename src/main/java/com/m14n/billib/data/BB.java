package com.m14n.billib.data;

import com.m14n.ex.Ex;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BB {
    public static final String DATA_ROOT = "data";

    public static final String CHART_DATE_HTML_FORMAT_STRING = "MMMM dd, yyyy";
    public static final String CHART_DATE_FORMAT_STRING = "yyyy-MM-dd";

    public static final DateFormat CHART_DATE_HTML_FORMAT = new SimpleDateFormat(CHART_DATE_HTML_FORMAT_STRING);
    public static final DateFormat CHART_DATE_FORMAT = new SimpleDateFormat(CHART_DATE_FORMAT_STRING);
    public static final String OLD_LAST_WEEK_NEWBY = "--";
    public static final String LAST_WEEK_NEWBY = "-";

    public static int extractLastWeekRank(String lastWeekStr) {
        if (Ex.isNotEmpty(lastWeekStr) && !BB.LAST_WEEK_NEWBY.equals(lastWeekStr) &&
                !BB.OLD_LAST_WEEK_NEWBY.equals(lastWeekStr)) {
            try {
                return Integer.valueOf(lastWeekStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

}
