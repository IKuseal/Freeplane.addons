package org.freeplane.features.gtd.intent.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.intent.GtdCoverIntentController
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

class RemoveTopGtdIntentAction() : AMultipleGtdNodeCoverAction("Gtd.RemoveTopGtdIntentAction") {

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        GtdCoverIntentController.removeTopIntent(node)
    }
}