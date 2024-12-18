package org.freeplane.features.gtd.filter.layersfilter

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.AGtdAction
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class EnableGtdLayerLevelConditionAction(val level: Int) : AGtdAction(generateKey(level), generateText(level)) {
    override fun actionPerformed(e: ActionEvent?) {
        setSelected()
        GtdLayersFilterController.enableLayerLevelCondition(level, !isSelected)
    }

    companion object {
        fun generateKey(level : Int) = "${EnableGtdLayerLevelConditionAction::class.simpleName}.$level"

        fun generateText(level : Int) = "Layer-$level"
    }

    override fun setSelected() {
        isSelected = GtdLayersFilterController.filterData?.enabledLayerLevels?.get(level) ?: false
    }
}