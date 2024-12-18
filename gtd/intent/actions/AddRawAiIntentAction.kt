package org.freeplane.features.gtd.intent.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.data.elements.GtdNum.ND
import org.freeplane.features.gtd.data.elements.GtdStateClass.I
import org.freeplane.features.gtd.intent.createNewEmptyIntent
import org.freeplane.features.gtd.layers.GcsType.GLOB
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.tag.GTD_PLANE_MEMO
import java.awt.event.ActionEvent

class AddRawAiIntentAction() : AMultipleGtdNodeCoverAction("Gtd.AddRawAiIntentAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        node.createNewEmptyIntent(true).also {
            it.state = I(ND)
            it.addTag(GTD_PLANE_MEMO)
            it.gcsType = GLOB
        }
    }
}