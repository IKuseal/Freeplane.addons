package org.freeplane.features.gtd.conditions.base

import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.node.GtdNodeCover

class GtdStateCondition(state : GtdState) : GtdStatusCondition<GtdState>(state) {
    override fun checkIntent(node: GtdNodeCover, intent: GtdNodeCover.GtdIntentCover) = intent.state == status
}