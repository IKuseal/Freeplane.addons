package org.freeplane.features.logistics

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.logistics.delivery.DeliveryController
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SwitchToNavigateToDestinationAction : AFreeplaneAction("Logistics.SwitchToNavigateToDestinationAction") {
    override fun actionPerformed(e: ActionEvent?) {
        DeliveryController.toNavigateToDestination = !DeliveryController.toNavigateToDestination
    }

    override fun setSelected() {
        isSelected = DeliveryController.toNavigateToDestination
    }
}