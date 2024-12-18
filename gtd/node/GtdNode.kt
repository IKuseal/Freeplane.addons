package org.freeplane.features.gtd.node

import org.freeplane.core.extension.IExtension
import org.freeplane.features.gtd.intent.GtdIntent
import org.freeplane.features.gtd.intent.IGtdIntent
import org.freeplane.features.map.NodeModel

abstract class GtdNode : IExtension {

    abstract val node : NodeModel

    abstract val intents: Collection<IGtdIntent>

    abstract fun copy() : GtdNode
}