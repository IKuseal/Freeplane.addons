package org.freeplane.features.gtd.map.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeAction
import org.freeplane.features.gtd.map.GtdMapController
import org.freeplane.features.gtd.node.GtdNodeModel
import java.awt.event.ActionEvent

class HardResetGtdNodeAction() : AMultipleGtdNodeAction("Gtd.HardResetGtdNodeAction") {

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeModel) {
        GtdMapController.resetNode(node)
    }
}