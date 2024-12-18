package org.freeplane.features.gtd.intent.actions

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import org.freeplane.features.gtd.map.cover.GtdTagCreationParamsAccessor
import org.freeplane.features.gtd.tag.GtdTag
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class EnableTagCreationParamAction(val tag : GtdTag)
    : AGtdAction(generateKey(tag), tag.name) {

    private val accessor : GtdTagCreationParamsAccessor
        get() = GtdMapCoverController.currentMapCover.tagCreationParamsAccessor

    override fun actionPerformed(e: ActionEvent?) {
        accessor.switchTag(tag)
    }

    companion object {
        fun generateKey(tag : GtdTag) = EnableTagCreationParamAction::class.simpleName + "." + tag.name
    }

    override fun setSelected() {
        isSelected = accessor.isTagEnabled(tag)
    }
}