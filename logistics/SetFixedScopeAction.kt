package org.freeplane.features.logistics

import org.freeplane.features.custom.actions.ANodeModelAction
import org.freeplane.features.map.NodeModel
import java.awt.event.ActionEvent

class SetFixedScopeAction : ANodeModelAction("Logistics.SetFixedScopeAction") {
    override fun actionPerformed(e: ActionEvent?, node: NodeModel) {
        LogisticsController.fixedScopeNode = node
    }
}