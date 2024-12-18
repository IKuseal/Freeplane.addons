package org.freeplane.features.custom

import org.freeplane.core.ui.AFreeplaneAction
import java.awt.event.ActionEvent

class UpdateMapStyleOfMapsInSpaceAction : AFreeplaneAction("Custom.UpdateMapStyleOfMapsInSpaceAction") {
    override fun actionPerformed(e: ActionEvent?) {
        replaceMapStylesInSpace(gtdMapStyleTemplatePath)
    }
}