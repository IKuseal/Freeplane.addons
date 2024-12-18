package org.freeplane.features.gtd.transparency

import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import org.freeplane.view.swing.features.custom.gtd.statusesfilterdialog.GtdStatusesFilterDialog
import java.awt.event.ActionEvent

class ShowGtdTransparencyDialogAction() : AGtdAction("Gtd.ShowGtdTransparencyDialogAction") {
    private val dialogTitle = "transparency conditions"

    override fun actionPerformed(e: ActionEvent?) {
        val mapCover = GtdMapCoverController.currentMapCover
        val transparencyCondition = GtdTransparencyController.transparencyConditionData(mapCover)

        GtdStatusesFilterDialog(GtdController.frame, dialogTitle, transparencyCondition).run {
            pack()
            setLocation(frame.width/2 - width/2,
                frame.height/2 - height/2)
            show()
        }

        GtdTransparencyController.updateTransparency(mapCover)
    }
}