package org.freeplane.features.gtd.preprocess

import org.freeplane.features.custom.map.extensions.SpreadBuilder
import org.freeplane.features.custom.map.extensions.TreeSpread
import org.freeplane.features.ankikt.xmlentity.XmlEntityAiNoteExtension
import org.freeplane.features.custom.preprocess.XmlElementModel
import org.freeplane.features.custom.preprocess.XmlNodeModel
import org.freeplane.features.gtd.readwrite.GtdNodeBuilder as BUILDER
import org.jsoup.nodes.Element

class XmlGtdNode (xml : Element, val node : XmlNodeModel) : XmlElementModel(xml) {
    val module : XmlGtdModule? by lazy {
        childXmlWithTag(XmlGtdModule.TAG)?.let { XmlGtdModule(it, this) }
    }

    val intents : List<XmlGtdIntent> by lazy {
        arrayListOf<XmlGtdIntent>().also {
            it.addAll(
                childrenXmlWithTag(XmlGtdIntent.TAG).map { XmlGtdIntent(it, this) }
            )
        }
    }

    val layer : XmlGtdLayer? by lazy {
        childrenXmlWithTag(XmlGtdLayer.TAG)
            .filter { it.attr(SpreadBuilder.ATTR) == TreeSpread.OWN.name }
            .firstOrNull()
            ?.let { XmlGtdLayer(it, this) }
    }

    companion object {
        val TAG get() = BUILDER.TAG
        val extKey get() = XmlGtdNode::class
    }
}

val XmlNodeModel.gtdNode : XmlGtdNode? get() {
    val key = XmlGtdNode.extKey
    if(extensions.containsKey(key)) {
        return extensions[key] as XmlGtdNode?
    }
    else {
        val gtdNode = childXmlWithTag(XmlGtdNode.TAG)
            ?.let { XmlGtdNode(it, this) }

        extensions[key] = gtdNode

        return gtdNode
    }
}

val XmlNodeModel.aiNoteExtension : XmlEntityAiNoteExtension? get() {
    val key = XmlEntityAiNoteExtension.extKey
    if(extensions.containsKey(key)) {
        return extensions[key] as XmlEntityAiNoteExtension?
    }
    else {
        val value = childXmlWithTag(XmlEntityAiNoteExtension.TAG)
            ?.let { XmlEntityAiNoteExtension(it, this) }

        extensions[key] = value

        return value
    }
}