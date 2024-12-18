package org.freeplane.features.gtd.module

import org.freeplane.features.gtd.conditions.base.GtdNodeCoverCondition
import org.freeplane.features.gtd.node.GtdNodeCover

class GtdModuleCondition : GtdNodeCoverCondition() {
    override val label: String
        get() = "Module condition"

    override fun checkGtdNodeCover(node: GtdNodeCover) = node.gtdNodeModel.module != null
}