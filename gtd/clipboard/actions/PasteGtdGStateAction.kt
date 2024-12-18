package org.freeplane.features.gtd.clipboard.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.clipboard.GtdClipboardController
import org.freeplane.features.gtd.intent.GtdIntentController
import org.freeplane.features.gtd.map.GtdMapController
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

class PasteGtdGStateAction()
    : AMultipleGtdNodeCoverAction("Gtd.PasteGtdGStateAction") {

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        GtdClipboardController.pasteGState(node)
    }
}