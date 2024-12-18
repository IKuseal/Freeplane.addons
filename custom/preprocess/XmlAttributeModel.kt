package org.freeplane.features.custom.preprocess

import org.jsoup.nodes.Element

class XmlAttributeModel(xml: Element, val node: XmlNodeModel) : XmlElementModel(xml) {
    val name : String = xml.attr(NAME_ATTR)
    val value : String = xml.attr(VALUE_ATTR)

    companion object {
        const val TAG = "attribute"
        const val NAME_ATTR = "NAME"
        const val VALUE_ATTR = "VALUE"
    }
}