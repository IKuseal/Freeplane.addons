package org.freeplane.features.gtd.conditions.base

import org.freeplane.features.gtd.node.GtdIntentCover
import org.freeplane.features.gtd.node.GtdNodeCover

abstract class GtdTopIntentCondition : GtdNodeCoverCondition() {
    override fun checkGtdNodeCover(node : GtdNodeCover) =
        checkIntent(node, node.intentOrFake)

    abstract fun checkIntent(gNodeC : GtdNodeCover, intent : GtdIntentCover) : Boolean
}