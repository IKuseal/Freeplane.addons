package org.freeplane.features.gtd.preprocess

import org.freeplane.features.custom.preprocess.XmlElementModel
import org.freeplane.features.gtd.layers.GcsType
import org.jsoup.nodes.Element
import org.freeplane.features.gtd.readwrite.GtdLayerBuilder as BUILDER

class XmlGtdLayer(xml: Element, val gtdNode: XmlGtdNode) : XmlElementModel(xml) {
    val gcsType: GcsType by lazy { GcsType.valueOf(xml.attr(BUILDER.GCS_TYPE)) }

    companion object {
        val TAG get() = BUILDER.TAG
    }
}