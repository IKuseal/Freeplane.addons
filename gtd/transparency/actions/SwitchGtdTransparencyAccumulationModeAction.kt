package org.freeplane.features.gtd.transparency.actions

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.transparency.GtdTransparencyController
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SwitchGtdTransparencyAccumulationModeAction : AGtdAction("Gtd.SwitchGtdTransparencyAccumulationModeAction") {
    override fun actionPerformed(e: ActionEvent?) {
        setSelected()
        GtdTransparencyController.createTransparencyData().isAccumulationMode = !isSelected
    }

    override fun setSelected() {
        isSelected = GtdTransparencyController.createTransparencyData().isAccumulationMode
    }
}