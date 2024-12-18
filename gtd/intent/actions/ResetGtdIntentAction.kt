package org.freeplane.features.gtd.intent.actions

import org.freeplane.features.gtd.actions.AMultipleGtdIntentCoverAction
import org.freeplane.features.gtd.intent.GtdCoverIntentController
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

class ResetGtdIntentAction : AMultipleGtdIntentCoverAction("Gtd.ResetGtdIntentAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover, intent: GtdNodeCover.GtdIntentCover) {
        GtdCoverIntentController.resetIntent(node, intent)
    }
}