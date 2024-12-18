package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

class LightResetGtdNodeAction() : AMultipleGtdNodeCoverAction("Gtd.LightResetGtdNodeAction") {

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        GtdMapCoverController.resetNode(node)
    }
}