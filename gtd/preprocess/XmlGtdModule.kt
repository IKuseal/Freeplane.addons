package org.freeplane.features.gtd.preprocess

import org.freeplane.features.custom.preprocess.XmlElementModel
import org.freeplane.features.custom.preprocess.XmlMapModel
import org.freeplane.features.custom.preprocess.XmlNodeModel
import org.freeplane.features.custom.preprocess.stream
import org.freeplane.features.custom.treestream.filter
import org.freeplane.features.gtd.module.MODULE_DEPTH_LIMIT
import org.freeplane.features.gtd.readwrite.GtdModuleBuilder as BUILDER
import org.jsoup.nodes.Element

class XmlGtdModule(xml: Element, val gtdNode: XmlGtdNode) : XmlElementModel(xml) {
    companion object {
        val TAG get() = BUILDER.TAG
    }
}

val XmlMapModel.moduleRoots : Collection<XmlNodeModel> get() =
    root.stream()
        .apply { depthLimit = MODULE_DEPTH_LIMIT }
        .filter(true) {
            it.gtdNode?.module != null
        }