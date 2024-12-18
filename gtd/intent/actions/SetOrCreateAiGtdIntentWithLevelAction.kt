package org.freeplane.features.gtd.intent.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.data.elements.GtdNum
import org.freeplane.features.gtd.data.elements.GtdStateClass.R
import org.freeplane.features.gtd.intent.GtdIntentController
import org.freeplane.features.gtd.intent.createNewEmptyIntent
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.tag.GTD_PLANE_MEMO
import java.awt.event.ActionEvent

class SetOrCreateAiGtdIntentWithLevelAction(
    val level : GtdNum
) : AMultipleGtdNodeCoverAction("Gtd.SetOrCreateAiGtdIntentWithLevelAction.${level.name}", level.name) {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        val intent = node.gtdNodeModel.intents.firstOrNull { it.containsTag(GTD_PLANE_MEMO) }
        val state = R(level)

        if(intent != null)
            GtdIntentController.setState(node.gtdNodeModel, intent, state)
        else node.createNewEmptyIntent(true).also {
            it.state = R(level)
            it.addTag(GTD_PLANE_MEMO)
        }
    }
}