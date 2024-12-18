package org.freeplane.features.gtd.module.actions

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.AMultipleGtdNodeAction
import org.freeplane.features.gtd.module.GtdModuleController
import org.freeplane.features.gtd.module.GtdModuleController.selectedGtdModuleSafe
import org.freeplane.features.gtd.node.GtdNodeModel
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SwitchGtdModuleAction : AMultipleGtdNodeAction("Gtd.SwitchGtdModuleAction") {
    private var toCreate = false

    override fun actionPerformed(e: ActionEvent?) {
        setSelected()
        toCreate = !isSelected
        super.actionPerformed(e)
    }

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeModel) {
        setSelected()
        GtdModuleController.switchGtdModule(node, toCreate)
    }

    override fun setSelected() {
        isSelected = selectedGtdModuleSafe != null
    }
}