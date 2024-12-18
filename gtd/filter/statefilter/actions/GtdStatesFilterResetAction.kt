package org.freeplane.features.gtd.filter.statefilter.actions

import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.filter.statefilter.GtdStatesFilterController
import java.awt.event.ActionEvent

class GtdStatesFilterResetAction() : AGtdAction("Gtd.GtdStatesFilterResetAction") {
    override fun actionPerformed(e: ActionEvent?) {
        GtdStatesFilterController.reset()
    }
}