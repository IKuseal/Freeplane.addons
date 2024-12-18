package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import org.freeplane.features.gtd.map.cover.currentGtdMapCover
import java.awt.event.ActionEvent

class ResetAllIntentToTopAction()
    : AGtdAction("Gtd.ResetAllIntentToTopAction") {

    override fun actionPerformed(e: ActionEvent?) {
        GtdMapCoverController.resetAllIntentToTop(GtdController.currentMap.currentGtdMapCover)
    }
}