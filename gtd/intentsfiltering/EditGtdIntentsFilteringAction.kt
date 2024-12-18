package org.freeplane.features.gtd.intentsfiltering

import org.freeplane.features.gtd.actions.AGtdMapCoverAction
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.view.swing.features.custom.centered
import org.freeplane.view.swing.features.custom.gtd.intentsfiltering.EditIntentsFilteringConditionDialog

class EditGtdIntentsFilteringAction : AGtdMapCoverAction("Gtd.EditGtdIntentsFilteringAction") {

    override fun actionPerformed() {
        EditIntentsFilteringConditionDialog(GtdController.frame, gtdMapC.intentsFilteringConditionModel).run {
            pack()
            centered()
            isVisible = true
        }

        GtdIntentsFilteringController.onConditionModelChange(gtdMapC)
    }
}