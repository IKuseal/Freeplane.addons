package org.freeplane.features.gtd.layers.actions

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.layers.GtdLayerController
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class EnableDoShowGtdLayersAction : AGtdAction("Gtd.EnableDoShowGtdLayersAction") {
    override fun actionPerformed(e: ActionEvent?) {
        setSelected()
        GtdLayerController.enableDoShowLayers(GtdMapCoverController.currentGtdMapCover, !isSelected)
    }

    override fun setSelected() {
        isSelected = GtdMapCoverController.currentGtdMapCoverSafe?.doShowLayers ?: false
    }
}