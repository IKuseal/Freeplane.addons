package org.freeplane.features.gtd.map.cover

import org.freeplane.features.gtd.core.GtdController

object DelayedMapCoverUpdater {
    var isCalculationSet = false

    fun updateMap(map: GtdMapCover) {
        if(isCalculationSet) return

        isCalculationSet = true

        GtdController.invokeLater {
            GtdMapCoverController.updateMap(map)
            isCalculationSet = false
        }
    }
}