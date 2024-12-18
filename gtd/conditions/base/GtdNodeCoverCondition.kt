package org.freeplane.features.gtd.conditions.base

import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.features.gtd.node.currentCover

abstract class GtdNodeCoverCondition : GtdNodeCondition() {
    override fun checkGtdNode(node: GtdNodeModel) = checkGtdNodeCover(node.currentCover)

    abstract fun checkGtdNodeCover(node : GtdNodeCover) : Boolean
}