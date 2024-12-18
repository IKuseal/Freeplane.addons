package org.freeplane.features.gtd.map.event

import org.freeplane.features.gtd.map.GtdMap

interface IGtdMapChangeListener {
    fun onMapNodeChange(event : GtdNodeChangeEvent)
    fun onMapStructureChange(map : GtdMap)
    fun onMapChange(event: GtdMapChangeEvent)
}