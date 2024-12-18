package org.freeplane.features.gtd.allmaps

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.features.gtd.core.GtdController
import java.awt.event.ActionEvent

class ImportListOfGtdIsAttachedToGlobMaps : AFreeplaneAction("Gtd.ImportListOfGtdIsAttachedToGlobMaps") {
    override fun actionPerformed(e: ActionEvent?) {
        GtdMapsRegistry.insertListOfGtdIsAttachedToGlobMaps(GtdController.selectedNode)
    }
}