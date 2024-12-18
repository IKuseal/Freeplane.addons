package org.freeplane.features.gtd.module.actions

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.GTD_IS_MODULE_EXIST_PROP
import org.freeplane.features.gtd.module.GtdModule
import org.freeplane.features.gtd.module.GtdModuleController
import org.freeplane.features.gtd.module.GtdModuleController.selectedGtdModuleSafe
import org.freeplane.features.gtd.node.GtdNodeModel

@SelectableAction(checkOnPopup = true)
class EnableLayer0ToExportAction : AMultipleGtdModuleAction("Gtd.EnableLayer0ToExportAction") {
    init {
        registerEnabledProp(GTD_IS_MODULE_EXIST_PROP)
    }

    override fun actionPerformed(gtdNode: GtdNodeModel, module: GtdModule) {
        setSelected()
        GtdModuleController.enableLayer0ToExport(gtdNode, module, !isSelected)
    }

    override fun setSelected() {
        isSelected = selectedGtdModuleSafe?.layer0ToExport ?: false
    }
}