package org.freeplane.features.custom.workspread

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.custom.map.extensions.TreeSpread
import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.actions.GTD_IS_ACTIVE_PROP
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SetGtdWorkSpreadAction(val spread : TreeSpread) : AGtdAction(generateKey(spread), spread.name) {

    override fun actionPerformed(e: ActionEvent?) {
        WorkSpreadController.workSpread = spread
    }

    companion object {
        fun generateKey(spread : TreeSpread) = SetGtdWorkSpreadAction::class.simpleName + "." + spread.name
    }

    override fun setSelected() {
        isSelected = spread == WorkSpreadController.workSpread
    }
}