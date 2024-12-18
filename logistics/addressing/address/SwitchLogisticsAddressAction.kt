package org.freeplane.features.logistics.addressing.address

import org.freeplane.core.ui.AMultipleNodeAction
import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.logistics.addressing.AddressController
import org.freeplane.features.map.NodeModel
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SwitchLogisticsAddressAction() : AMultipleNodeAction("Logistics.SwitchLogisticsAddressAction") {
    var toEnable = false

    override fun actionPerformed(e: ActionEvent?) {
        toEnable = checkToEnable()
        super.actionPerformed(e)
    }

    override fun actionPerformed(e: ActionEvent?, node: NodeModel) {
        AddressController.switchAddress(node, toEnable)
    }

    override fun setSelected() {
        isSelected = !checkToEnable()
    }

    fun checkToEnable() = GlobalFrFacade.selectedNodeSafe?.logisticsAddress == null
}