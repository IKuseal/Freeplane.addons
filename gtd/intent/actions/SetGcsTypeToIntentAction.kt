package org.freeplane.features.gtd.intent.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.intent.GtdIntentController
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

class SetGcsTypeToIntentAction(val gcsType: GcsType) : AMultipleGtdNodeCoverAction(generateKey(gcsType), gcsType.toString(), null) {
    override fun actionPerformed(e: ActionEvent?, gtdNode: GtdNodeCover) {
        val intent = gtdNode.intentModel ?: return
        GtdIntentController.setGcsType(gtdNode.gtdNodeModel, intent, gcsType)
    }

    companion object {
        fun generateKey(gcsType : GcsType) = "${SetGcsTypeToIntentAction::class.simpleName}.${gcsType.name}"
    }
}