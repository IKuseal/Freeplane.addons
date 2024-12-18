package org.freeplane.features.logistics.collector

import org.freeplane.core.ui.AFreeplaneAction
import java.awt.event.ActionEvent

class SetLinkToCollectAction() : AFreeplaneAction("Logistics.SetLinkToCollectAction") {
    val provider get() = DataToCollectAccessor
    override fun actionPerformed(e: ActionEvent?) {
        val link = provider.run { if(isLinkData) getLink() else if(isTextData) getText() else null }
        link?.let { CollectorController.linkToCollect = it }
    }
}