package org.freeplane.features.gtd.conditions.base

import org.freeplane.features.gtd.data.elements.GtdSubState
import org.freeplane.features.gtd.node.GtdNodeCover

class GtdSubStateCondition(subState : GtdSubState) : GtdStatusCondition<GtdSubState>(subState) {
    override fun checkIntent(node: GtdNodeCover, intent: GtdNodeCover.GtdIntentCover) = intent.containsSubState(status.clazz)
}