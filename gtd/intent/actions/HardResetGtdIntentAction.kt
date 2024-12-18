package org.freeplane.features.gtd.intent.actions

import org.freeplane.features.gtd.actions.AMultipleGtdIntentCoverAction
import org.freeplane.features.gtd.data.elements.GtdNum
import org.freeplane.features.gtd.data.elements.GtdNum.ND
import org.freeplane.features.gtd.data.elements.GtdStateClass.I
import org.freeplane.features.gtd.intent.GtdCoverIntentController
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

class HardResetGtdIntentAction : AMultipleGtdIntentCoverAction("Gtd.HardResetGtdIntentAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover, intent: GtdNodeCover.GtdIntentCover) {
        GtdCoverIntentController.resetIntent(node, intent)
        GtdCoverIntentController.setState(node, intent.model, I(ND))
    }
}