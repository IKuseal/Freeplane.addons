package org.freeplane.features.gtd.conditions.base

import org.freeplane.features.filter.condition.ASelectableCondition
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.features.gtd.node.gtdNode
import org.freeplane.features.map.NodeModel

abstract class GtdNodeCondition : ASelectableCondition() {
    abstract val label : String

    override fun getName(): String = ""

    override fun checkNode(node: NodeModel) = checkGtdNode(node.gtdNode)

    abstract fun checkGtdNode(node : GtdNodeModel) : Boolean

    override fun createDescription(): String = label

    override fun hashCode(): Int {
        return label.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return this === other
    }
}