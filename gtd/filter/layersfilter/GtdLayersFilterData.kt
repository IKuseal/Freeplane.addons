package org.freeplane.features.gtd.filter.layersfilter

import org.freeplane.features.filter.condition.ASelectableCondition
import org.freeplane.features.filter.condition.DisjunctConditions
import org.freeplane.features.gtd.filter.AGtdSubFilterData
import org.freeplane.features.gtd.layers.GtdLayerLevelCondition
import org.freeplane.features.gtd.layers.MAX_LAYER_LEVEL


class GtdLayersFilterData : AGtdSubFilterData() {
    val enabledLayerLevels = Array(MAX_LAYER_LEVEL+1) {false}

    override val condition: ASelectableCondition?
        get() = enabledLayerLevels
            .mapIndexedNotNull() { index, enabled -> if(enabled) GtdLayerLevelCondition(index) else null }
            .takeIf { it.isNotEmpty() }
            ?.let { DisjunctConditions.combine(*it.toTypedArray()) }
}