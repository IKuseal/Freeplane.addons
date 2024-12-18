package org.freeplane.features.gtd.filter.statefilter.actions

import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.filter.statefilter.GtdStatesFilterController
import org.freeplane.view.swing.features.custom.gtd.statusesfilterdialog.GtdStatusesFilterDialog
import java.awt.event.ActionEvent

class ShowGtdStatesFilterDialogAction() : AGtdAction("Gtd.ShowGtdStatesFilterDialogAction") {
    private val dialogTitle = "States-filter compose"

    override fun actionPerformed(e: ActionEvent?) {
        val conditionsData = GtdStatesFilterController.createConditionsData()

        GtdStatusesFilterDialog(GtdController.frame, dialogTitle, conditionsData).run {
            pack()
            setLocation(frame.width/2 - width/2,
                frame.height/2 - height/2)
            show()
        }

        GtdStatesFilterController.onFilterDataUpdated()
    }
}