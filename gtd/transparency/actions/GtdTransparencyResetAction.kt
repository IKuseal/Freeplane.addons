package org.freeplane.features.gtd.transparency.actions

import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import org.freeplane.features.gtd.transparency.GtdTransparencyController
import java.awt.event.ActionEvent

class GtdTransparencyResetAction : AGtdAction("Gtd.GtdTransparencyResetAction") {

    override fun actionPerformed(e: ActionEvent?) {
        GtdTransparencyController.reset(GtdMapCoverController.currentMapCover)
    }
}