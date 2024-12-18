package org.freeplane.features.gtd.clipboard.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeAction
import org.freeplane.features.gtd.clipboard.GtdClipboardController
import org.freeplane.features.gtd.node.GtdNodeModel
import java.awt.event.ActionEvent

class PasteGtdNodeAction : AMultipleGtdNodeAction("Gtd.PasteGtdNodeAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeModel) {
        GtdClipboardController.paste(node)
    }
}