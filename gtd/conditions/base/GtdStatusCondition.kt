package org.freeplane.features.gtd.conditions.base

import org.freeplane.features.gtd.data.elements.GtdStatus
import org.freeplane.features.gtd.node.GtdNodeCover

abstract class GtdStatusCondition<T : GtdStatus>(val status : T) : GtdNodeCoverCondition() {
    override val label get() = status.name

    override fun checkGtdNodeCover(node: GtdNodeCover) = checkIntent(node)

    abstract fun checkIntent(node : GtdNodeCover, intent : GtdNodeCover.GtdIntentCover = node.intentOrFake) : Boolean
}