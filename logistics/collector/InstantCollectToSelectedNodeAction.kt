package org.freeplane.features.logistics.collector

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.features.custom.GlobalFrFacade
import java.awt.event.ActionEvent

class InstantCollectToSelectedNodeAction : AFreeplaneAction("Logistics.InstantCollectToSelectedNodeAction") {
    override fun actionPerformed(e: ActionEvent?) {
        val destinationNode = GlobalFrFacade.selectedNode

        CollectorController.instantCollect(destinationNode, false)
    }
}