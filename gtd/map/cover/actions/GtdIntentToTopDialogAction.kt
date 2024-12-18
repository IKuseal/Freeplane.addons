package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.gtd.actions.AGtdNodeCoverAction
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.view.swing.features.custom.centered
import org.freeplane.view.swing.features.custom.gtd.intenttotop.GtdIntentToTopDialog
import java.awt.event.ActionEvent

class GtdIntentToTopDialogAction : AGtdNodeCoverAction("Gtd.GtdIntentToTopDialogAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        val dialog = GtdIntentToTopDialog(GlobalFrFacade.frame, node.intents).apply {
            pack()
            centered()
            isVisible = true
        }

        if(dialog.isSuccess) {
            GtdMapCoverController.setIntentToTop(node, dialog.result?.model)
        }
    }
}