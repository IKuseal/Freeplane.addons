package org.freeplane.features.gtd.map.cover.event

import org.freeplane.features.gtd.core.GtdEventProp
import org.freeplane.features.gtd.map.cover.GtdMapCover
import org.freeplane.features.gtd.node.GtdNodeCover

object GtdMapCoverChangeAnnouncer : IGtdMapCoverChangeAnnouncer {
    private val listeners : MutableCollection<IGtdMapCoverChangeListener> = arrayListOf()

    override fun addGtdMapCoverChangeListener(l : IGtdMapCoverChangeListener) = listeners.add(l)

    override fun fireGtdMapCoverChange(map : GtdMapCover, prop : GtdEventProp) {
        listeners.forEach { it.onGtdMapCoverChange(map, prop) }
    }
}