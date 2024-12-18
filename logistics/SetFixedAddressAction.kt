package org.freeplane.features.logistics

import org.freeplane.features.custom.actions.ANodeModelAction
import org.freeplane.features.map.NodeModel
import java.awt.event.ActionEvent

class SetFixedAddressAction : ANodeModelAction("Logistics.SetFixedAddressAction") {
    override fun actionPerformed(e: ActionEvent?, node: NodeModel) {
        LogisticsController.fixedAddressNode = node
    }
}