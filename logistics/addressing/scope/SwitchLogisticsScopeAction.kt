package org.freeplane.features.logistics.addressing.scope

import org.freeplane.core.ui.AMultipleNodeAction
import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.logistics.addressing.AddressController
import org.freeplane.features.map.NodeModel
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SwitchLogisticsScopeAction() : AMultipleNodeAction("Logistics.SwitchLogisticsScopeAction") {
    var toEnable = false

    override fun actionPerformed(e: ActionEvent?) {
        toEnable = checkToEnable()
        super.actionPerformed(e)
    }

    override fun actionPerformed(e: ActionEvent?, node: NodeModel) {
        AddressController.switchScope(node, toEnable)
    }

    override fun setSelected() {
        isSelected = !checkToEnable()
    }

    fun checkToEnable() = GlobalFrFacade.selectedNodeSafe?.logisticsScope == null
}