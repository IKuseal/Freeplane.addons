package org.freeplane.features.gtd.glob.actions

import org.freeplane.core.ui.AMultipleNodeAction
import org.freeplane.features.custom.map.stream
import org.freeplane.features.custom.treestream.nextAncestor
import org.freeplane.features.gtd.actions.GTD_IS_GLOB_MAP_SELECTED_PROP
import org.freeplane.features.gtd.glob.GtdGlobController
import org.freeplane.features.gtd.module.GtdModuleCondition
import org.freeplane.features.map.NodeModel
import java.awt.event.ActionEvent

class ReattachGlobModuleAction : AMultipleNodeAction("Gtd.ReattachGlobModuleAction") {
    init {
        registerEnabledProp(GTD_IS_GLOB_MAP_SELECTED_PROP)
    }

    override fun actionPerformed(e: ActionEvent?, node: NodeModel) {
        node.stream().nextAncestor(GtdModuleCondition()::checkNode)?.let {
            GtdGlobController.reattachModule(it)
        }
    }
}