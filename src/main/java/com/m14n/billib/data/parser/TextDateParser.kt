package com.m14n.billib.data.parser

import java.text.DateFormat
import java.text.ParseException
import java.util.*

interface TextDateParser {

    /**
     * Parses the given text and converts it into [Date]
     * @param textDate Date in the text form
     * @return Date which corresponds to the given text
     * @throws ParseException in case text can not be converted into [Date]
     */
    @Throws(ParseException::class)
    fun parse(textDate: String): Date
}

class DateFormatParser(
    private val dateFormat: DateFormat
) : TextDateParser {
    override fun parse(textDate: String): Date = dateFormat.parse(textDate)
}
