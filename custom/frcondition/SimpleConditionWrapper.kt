package org.freeplane.features.custom.frcondition

import org.freeplane.features.filter.condition.ASelectableCondition
import org.freeplane.features.map.NodeModel

class SimpleConditionWrapper(var condition : ASelectableCondition) : ASelectableCondition() {
    override fun checkNode(node: NodeModel) = condition.checkNode(node)

    override fun createDescription() = "$condition"

    override fun getName() = "undefined"
}