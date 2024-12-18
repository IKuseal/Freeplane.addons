package org.freeplane.features.gtd.clipboard.actions

import org.freeplane.features.gtd.actions.AGtdNodeCoverAction
import org.freeplane.features.gtd.clipboard.GtdClipboardController
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

class CopyGtdNodeAction : AGtdNodeCoverAction("Gtd.CopyGtdNodeAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        GtdClipboardController.copy(node)
    }
}