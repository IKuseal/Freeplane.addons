package org.freeplane.features.gtd.glob

import org.freeplane.features.gtd.node.gtdNode
import org.freeplane.features.map.NodeModel

class GlobModuleData(val node : NodeModel) {
    val module = node.gtdNode.module!!
    val id get() = module.id
}