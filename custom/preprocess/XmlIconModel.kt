package org.freeplane.features.custom.preprocess

import org.jsoup.nodes.Element

class XmlIconModel(xml: Element, val node: XmlNodeModel) : XmlElementModel(xml) {
    val id : String = xml.attr(BUILTIN_ATTR)

    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(other !is XmlIconModel) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        const val TAG = "icon"
        const val BUILTIN_ATTR = "BUILTIN"
    }
}