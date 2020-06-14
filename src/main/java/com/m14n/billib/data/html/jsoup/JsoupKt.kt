package com.m14n.billib.data.html.jsoup

import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import java.text.ParseException

/**
 * Extension for [Element] to request for an element to avoid non-informative Java `null` results.
 * @param id Identifier for an embedded element to search for
 * @return Element with the requested identifier
 * @throws ParseException in case the element has not been found
 */
@Throws(ParseException::class)
fun Element.requestElementById(id: String): Element = getElementById(id)
        ?: throw ParseException("Element with the id \"${id}\" has not been found inside the element \"${tagName()}\"", -1)

/**
 * Extension for [Node] to request for an attribute to avoid non-informative Java `null` results.
 * @param key Attribute key to search for
 * @return Attribute with the requested key
 * @throws ParseException in case the attribute has not been found
 */
fun Node.requestAttr(key: String): String = attr(key)
        ?: throw ParseException("Attribute with the key \"${key}\" has not been found inside the node \"${nodeName()}\"", -1)