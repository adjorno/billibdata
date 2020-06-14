package com.m14n.billib.data.html.jsoup

import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.select.Elements
import java.text.ParseException

/**
 * Extension for [Element] to request for an element by id to avoid non-informative Java `null` results.
 * @param id Identifier for an embedded element to search for
 * @return Element with the requested identifier
 * @throws ParseException in case the element has not been found
 */
@Throws(ParseException::class)
fun Element.requestElementById(id: String): Element = getElementById(id)
    ?: throw ParseException("Element with the id \"${id}\" has not been found inside the element \"${tagName()}\"", -1)

/**
 * Extension for [Element] to request for elements by class to avoid non-informative Java `null` results.
 * @param className Class name for embedded elements to search for
 * @return Elements with the requested class name
 * @throws ParseException in case no elements have been found
 */
@Throws(ParseException::class)
fun Element.requestElementsByClass(className: String): Elements = getElementsByClass(className).also { elements ->
    if (elements.isEmpty()) {
        throw ParseException(
            "Elements with the class \"${className}\" have not been found inside the element \"${tagName()}\"",
            -1
        )
    } else {
        elements
    }
}

/**
 * Extension for [Element] to request for elements by tag to avoid non-informative Java `null` results.
 * @param tag Tag for embedded elements to search for
 * @return Elements with the requested tag
 * @throws ParseException in case no elements have been found
 */
@Throws(ParseException::class)
fun Element.requestElementsByTag(tag: String): Elements = getElementsByTag(tag).also { elements ->
    if (elements.isEmpty()) {
        throw ParseException(
            "Elements with the tag \"${tag}\" have not been found inside the element \"${tagName()}\"",
            -1
        )
    } else {
        elements
    }
}

/**
 * Extension for [Node] to request for an attribute to avoid non-informative Java `null` results.
 * @param key Attribute key to search for
 * @return Attribute with the requested key
 * @throws ParseException in case the attribute has not been found
 */
fun Node.requestAttr(key: String): String = attr(key)
    ?: throw ParseException(
        "Attribute with the key \"${key}\" has not been found inside the node \"${nodeName()}\"",
        -1
    )

/**
 * Extension function to fetch value of the only text node
 */
fun Element.nodeText() = textNodes().first().text().trim()