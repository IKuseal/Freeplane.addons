package org.freeplane.features.gtd.node

import org.freeplane.features.gtd.tag.GtdPlane

interface IntentsOrderStrategy {
    fun orderedIntents(intents : Collection<GtdIntentCover>) : List<GtdIntentCover>
    val topmostPlane: GtdPlane
}