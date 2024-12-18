package org.freeplane.features.gtd.glob.actions

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.features.gtd.glob.GtdGlobController
import java.awt.event.ActionEvent

class LoadGtdGlobMapAction : AFreeplaneAction("Gtd.LoadGtdGlobMapAction") {
    override fun actionPerformed(e: ActionEvent?) {
        GtdGlobController.loadGlobMap()
    }
}