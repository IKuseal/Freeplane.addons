package org.freeplane.features.gtd.preprocess

import org.freeplane.features.custom.preprocess.XmlElementModel
import org.freeplane.features.custom.preprocess.XmlMapModel
import org.jsoup.nodes.Element
import org.freeplane.features.gtd.configuration.GtdConfigurationBuilder as BUILDER

class XmlGtdConfiguration(xml: Element, val map: XmlMapModel) : XmlElementModel(xml) {
    val attachedToGlobal: Boolean = xml.attr(BUILDER.ATTACHED_TO_GLOB).toBoolean()

    companion object {
        val TAG get() = BUILDER.TAG
    }
}
