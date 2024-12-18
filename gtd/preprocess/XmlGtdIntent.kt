package org.freeplane.features.gtd.preprocess

import org.freeplane.features.custom.preprocess.XmlElementModel
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.readwrite.GtdIntentBuilder
import org.freeplane.features.gtd.tag.GtdTag
import org.freeplane.features.gtd.tag.extWorkBuiltinTagsByName
import org.freeplane.features.gtd.readwrite.GtdIntentBuilder as BUILDER
import org.jsoup.nodes.Element

class XmlGtdIntent(xml: Element, val gtdNode: XmlGtdNode) : XmlElementModel(xml) {
    val gcsType: GcsType by lazy { GcsType.valueOf(xml.attr(BUILDER.GCS_TYPE_ATTR)) }

    val tags : Collection<GtdTag> by lazy {
        arrayListOf<GtdTag>().apply {
            xml.attr(GtdIntentBuilder.TAGS_ATTR)
                .split(",")
                .map { extWorkBuiltinTagsByName[it]!! }
                .let { this.addAll(it) }
        }
    }

    companion object {
        val TAG get() = BUILDER.TAG
    }
}