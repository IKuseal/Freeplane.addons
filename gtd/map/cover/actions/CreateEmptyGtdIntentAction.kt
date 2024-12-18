package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.intent.createNewEmptyIntent
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

class CreateEmptyGtdIntentAction : AMultipleGtdNodeCoverAction("Gtd.CreateEmptyGtdIntentAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        node.createNewEmptyIntent(true)
    }
}