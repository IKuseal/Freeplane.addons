package org.freeplane.features.custom.preprocess

import org.jsoup.nodes.Element

abstract class XmlElementModel(val xml : Element) {
    fun childrenXmlWithTag(tagName: String) : List<Element> =
        xml.children().filter { it -> it.tagName() == tagName }

    fun childXmlWithTag(tagName : String) : Element? =
        childrenXmlWithTag(tagName).firstOrNull()
}