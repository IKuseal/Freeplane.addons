package org.freeplane.features.logistics.collector

import org.freeplane.core.ui.AFreeplaneAction
import java.awt.event.ActionEvent

class ResetLinkToCollectAction() : AFreeplaneAction("Logistics.ResetLinkToCollectAction") {
    override fun actionPerformed(e: ActionEvent?) {
        CollectorController.linkToCollect = ""
    }
}