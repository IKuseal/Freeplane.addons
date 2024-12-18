package org.freeplane.features.gtd.filter.modulefilter

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.AGtdAction
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SwitchGtdModuleFiltering : AGtdAction("Gtd.SwitchGtdModuleFiltering") {
    override fun actionPerformed(e: ActionEvent?) {
        setSelected()
        GtdModuleFilterController.enableFiltering(!isSelected)
    }

    override fun setSelected() {
        isSelected = GtdModuleFilterController.filterData?.isActivated ?: false
    }
}