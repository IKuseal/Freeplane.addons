package org.freeplane.features.gtd.intent.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.intent.GtdIntentController
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.tag.GtdTag
import java.awt.event.ActionEvent

class RemoveGtdIntentsWithTagAction(
    val tag : GtdTag
) : AMultipleGtdNodeCoverAction(generateKey(tag), tag.name) {

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        val gNodeM = node.gtdNodeModel
        gNodeM.intents.forEach {
            if(it.containsTag(tag))
                GtdIntentController.removeIntent(gNodeM, it)
        }
    }

    companion object {
        fun generateKey(tag : GtdTag) = RemoveGtdIntentsWithTagAction::class.simpleName + "." + tag.name
    }
}