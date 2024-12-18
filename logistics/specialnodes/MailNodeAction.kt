package org.freeplane.features.logistics.specialnodes

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.features.custom.GlobalFrFacade
import java.awt.event.ActionEvent

class MailNodeAction : AFreeplaneAction("Logistics.MailNodeAction") {
    override fun actionPerformed(e: ActionEvent?) {
        val node = GlobalFrFacade.selectedNode.createMailNode()
        GlobalFrFacade.mapController.select(node)
    }
}