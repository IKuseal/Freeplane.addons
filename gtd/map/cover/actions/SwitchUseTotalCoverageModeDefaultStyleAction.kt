package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.AGtdMapCoverAction
import org.freeplane.features.gtd.map.cover.GtdMapCoverController

@SelectableAction(checkOnPopup = true)
class SwitchUseTotalCoverageModeDefaultStyleAction : AGtdMapCoverAction("Gtd.SwitchUseTotalCoverageModeDefaultStyleAction") {
    override fun actionPerformed() {
        GtdMapCoverController.switchUseTotalCoverageModeDefaultStyle(gtdMapC)
    }

    override fun setSelected() {
        isSelected = GtdMapCoverController.currentGtdMapCoverSafe?.useTotalCoverageModeDefaultStyle ?: false
    }
}