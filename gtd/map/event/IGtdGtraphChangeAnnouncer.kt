package org.freeplane.features.gtd.map.event

import org.freeplane.features.gtd.core.GtdEventProp
import org.freeplane.features.gtd.map.GtdMap
import org.freeplane.features.gtd.map.GtdMapController
import org.freeplane.features.gtd.node.GtdNodeModel

interface IGtdMapChangeAnnouncer {
    fun addMapChangeListener(listener : IGtdMapChangeListener)
    fun fireNodeChange(node : GtdNodeModel, prop : GtdEventProp = GtdMapController.gtdNodeEventUndefinedProp)
    fun fireMapChange(map : GtdMap, prop : GtdEventProp = GtdMapController.gtdMapEventUndefinedProp,
                      setsDirtyFlag : Boolean = true)
}