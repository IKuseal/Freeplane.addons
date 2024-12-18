package org.freeplane.features.logistics.collector

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.features.logistics.LogisticsController
import java.awt.event.ActionEvent

class InstantCollectAction : AFreeplaneAction("Logistics.InstantCollectAction") {
    override fun actionPerformed(e: ActionEvent?) {
        LogisticsController.fixedAddress ?: return

        val destinationNode = LogisticsController.fixedAddressNode!!

        CollectorController.instantCollect(destinationNode)
    }
}