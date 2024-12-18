package org.freeplane.features.custom.preprocess

import org.jsoup.nodes.Element

class XmlImageModel(xml: Element, val node: XmlNodeModel) : XmlElementModel(xml) {
    val path : String = xml.attr(URI_ATTR)
    val name : String = path.split("/").last()

    companion object {
        const val TAG = "hook"
        const val URI_ATTR = "URI"
    }
}