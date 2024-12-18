package org.freeplane.features.gtd.filter.statefilter.actions

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.filter.statefilter.GtdStatesFilterController
import org.freeplane.features.gtd.transparency.GtdTransparencyController
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SwitchGtdStatesFilterAccumulationModeAction : AGtdAction("Gtd.SwitchGtdStatesFilterAccumulationModeAction") {
    private var isAccumulationMode : Boolean
        get() = GtdStatesFilterController.createFilterData().isAccumulationMode
        set(value) {
            GtdStatesFilterController.createFilterData().isAccumulationMode = value
        }

    override fun actionPerformed(e: ActionEvent?) {
        isAccumulationMode = !isAccumulationMode
    }

    override fun setSelected() {
        isSelected = isAccumulationMode
    }
}