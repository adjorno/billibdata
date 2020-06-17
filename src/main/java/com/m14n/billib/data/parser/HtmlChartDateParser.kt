import com.m14n.billib.data.parser.TextDateParser
import com.m14n.billib.data.parser.countryDateParser
import org.jsoup.nodes.Document
import java.text.ParseException
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Default [HtmlChartDateParser] implementation.
 */
fun defaultDateParser(logger: Logger? = null): HtmlChartDateParser =
    countryDateParser().let { parser ->
        if (logger != null) {
            LogOnErrorChartDateParser(parser, logger)
        } else {
            parser
        }
    }

/**
 * Parses chart [Date] from chart html [Document]
 */
interface HtmlChartDateParser {
    @Throws(ParseException::class)
    fun parse(document: Document): Date
}

/**
 * Parses chart text date from chart html [Document]
 */
interface HtmlChartTextDateParser {
    @Throws(ParseException::class)
    fun parse(document: Document): String
}

/**
 * Basic [HtmlChartDateParser] implementation to delegate split steps
 */
class DelegateHtmlChartDateParser(
    private val htmlChartTextDateParser: HtmlChartTextDateParser,
    private val textDateParser: TextDateParser
) : HtmlChartDateParser {
    override fun parse(document: Document): Date =
        textDateParser.parse(htmlChartTextDateParser.parse(document))
}

/**
 * [HtmlChartDateParser] implementation to parse the [Document] via any of
 * supplied delegates.
 */
class CompositeHtmlChartDateParser(
    private val delegates: List<HtmlChartDateParser>
) : HtmlChartDateParser {
    override fun parse(document: Document): Date = delegates.asSequence()
        .mapNotNull { delegate ->
            try {
                delegate.parse(document)
            } catch (e: ParseException) {
                e.printStackTrace()
                null
            }
        }.firstOrNull() ?: throw ParseException("There are no parsers for the given html", -1)
}

/**
 * [HtmlChartDateParser] implementation to log the [Document] in case
 * of [ParseException]
 */
class LogOnErrorChartDateParser(
    private val delegate: HtmlChartDateParser,
    private val logger: Logger
) : HtmlChartDateParser {
    override fun parse(document: Document): Date = try {
        delegate.parse(document)
    } catch (e: ParseException) {
        logger.log(Level.WARNING, document.toString(), e)
        throw e
    }
}
