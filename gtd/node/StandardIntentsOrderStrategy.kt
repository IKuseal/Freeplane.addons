package org.freeplane.features.gtd.node

import org.freeplane.features.gtd.map.cover.tagsViewOrderAccessor
import org.freeplane.features.gtd.tag.GtdPlane

class StandardIntentsOrderStrategy(val gtdNodeCover: GtdNodeCover) : IntentsOrderStrategy {
    private val viewOrderAccessor get() = gtdNodeCover.gtdMapCover.tagsViewOrderAccessor

    override fun orderedIntents(intents: Collection<GtdIntentCover>): List<GtdIntentCover> {
        return viewOrderAccessor.orderedIntents(intents)
    }

    override val topmostPlane : GtdPlane get() = viewOrderAccessor.topmostPlane
}