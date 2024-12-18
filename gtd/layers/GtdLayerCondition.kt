package org.freeplane.features.gtd.layers

import org.freeplane.features.gtd.conditions.base.GtdNodeCoverCondition
import org.freeplane.features.gtd.node.GtdNodeCover

abstract class GtdLayerCondition : GtdNodeCoverCondition() {
    override fun checkGtdNodeCover(node: GtdNodeCover): Boolean =
        node.layer?.let { check(it) } ?: false

    abstract fun check(layer : GtdLayer) : Boolean
}