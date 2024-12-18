package org.freeplane.features.logistics.navigation

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.logistics.delivery.DeliveryController
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SwitchToNavigateToDestinationInNewViewAction : AFreeplaneAction("Logistics.SwitchToNavigateToDestinationInNewViewAction") {
    override fun actionPerformed(e: ActionEvent?) {
        NavigationController.toNavigateToDestinationInNewView = !NavigationController.toNavigateToDestinationInNewView
    }

    override fun setSelected() {
        isSelected = NavigationController.toNavigateToDestinationInNewView
    }
}