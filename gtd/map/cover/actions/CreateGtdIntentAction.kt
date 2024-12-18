package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.intent.createNewIntentUseTemplate
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

class CreateGtdIntentAction : AMultipleGtdNodeCoverAction("Gtd.CreateGtdIntentAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        node.createNewIntentUseTemplate(true)
    }
}