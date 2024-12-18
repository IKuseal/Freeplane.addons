package org.freeplane.features.gtd.intent.actions

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.AMultipleGtdIntentCoverAction
import org.freeplane.features.gtd.intent.GtdCoverIntentController
import org.freeplane.features.gtd.intent.GtdIntentController
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.tag.GtdTag
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class EnableGtdTagAction(
    val tag : GtdTag
) : AMultipleGtdIntentCoverAction(generateKey(tag), tag.name) {

    private var toEnable = true

    override fun actionPerformed(e: ActionEvent?) {
        toEnable = !selectedIntentContainsTag
        super.actionPerformed(e)
    }

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover, intent: GtdNodeCover.GtdIntentCover) {
        GtdIntentController.enableTag(node.gtdNodeModel, intent.model, tag, toEnable)
    }

    companion object {
        fun generateKey(tag : GtdTag) = EnableGtdTagAction::class.simpleName + "." + tag.name
    }

    override fun setSelected() {
        isSelected = selectedIntentContainsTag
    }

    private val selectedIntentContainsTag get() =
        GtdCoverIntentController.selectedIntent?.containsTag(tag) ?: false
}