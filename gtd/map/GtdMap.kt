package org.freeplane.features.gtd.map

import org.freeplane.core.extension.IExtension
import org.freeplane.features.gtd.node.gtdNode
import org.freeplane.features.map.MapModel

class GtdMap(val map : MapModel) : IExtension {
    val root get() = map.rootNode!!.gtdNode
}

val MapModel.gtdMap get() = getExtension(GtdMap::class.java) ?: GtdMap(this).also { putExtension(it) }