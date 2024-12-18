package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.AGtdMapCoverAction
import org.freeplane.features.gtd.map.cover.GtdMapCoverController

@SelectableAction(checkOnPopup = true)
class SwitchIsTotalCoverageModeAction : AGtdMapCoverAction("Gtd.SwitchIsTotalCoverageModeAction") {
    override fun actionPerformed() {
        GtdMapCoverController.switchTotalCoverageModeAction(gtdMapC)
    }

    override fun setSelected() {
        isSelected = GtdMapCoverController.currentGtdMapCoverSafe?.isTotalCoverageMode ?: false
    }
}