package org.freeplane.features.gtd.layers.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeAction
import org.freeplane.features.gtd.layers.GtdLayerController
import org.freeplane.features.gtd.node.GtdNodeModel
import java.awt.event.ActionEvent

class RemoveGtdLayerAction : AMultipleGtdNodeAction("Gtd.RemoveGtdLayerAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeModel) {
        GtdLayerController.removeLayer(node)
    }
}