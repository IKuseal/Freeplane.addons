package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.intent.GtdIntentController
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

class CreateGtdIntentFromDefaultIntentAction : AMultipleGtdNodeCoverAction("Gtd.CreateGtdIntentFromDefaultIntentAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        val intent = node.intent?.takeIf { it.model is GtdNodeCover.GtdIntentDefault } ?: return
        val intentM = intent.model
        val gNodeM = node.gtdNodeModel

        GtdIntentController.setGcsType(gNodeM, intentM, GcsType.LOCAL)
        GtdIntentController.setGcsType(gNodeM, intentM, GcsType.GLOB)
    }
}