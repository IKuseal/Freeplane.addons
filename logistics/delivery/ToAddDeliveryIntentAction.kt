package org.freeplane.features.logistics.delivery

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.logistics.delivery.DeliveryController
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class ToAddDeliveryIntentAction : AFreeplaneAction("Logistics.ToAddDeliveryIntentAction") {
    override fun actionPerformed(e: ActionEvent?) {
        DeliveryController.toAddDeliveryIntent = !DeliveryController.toAddDeliveryIntent
    }

    override fun setSelected() {
        isSelected = DeliveryController.toAddDeliveryIntent
    }
}