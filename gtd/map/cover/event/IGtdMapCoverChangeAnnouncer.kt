package org.freeplane.features.gtd.map.cover.event

import org.freeplane.features.gtd.core.GtdEventProp
import org.freeplane.features.gtd.map.cover.GtdMapCover
import org.freeplane.features.gtd.node.GtdNodeCover

interface IGtdMapCoverChangeAnnouncer {
    fun fireGtdMapCoverChange(map: GtdMapCover, prop : GtdEventProp)
    fun addGtdMapCoverChangeListener(l: IGtdMapCoverChangeListener): Boolean
}