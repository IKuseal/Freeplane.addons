package org.freeplane.features.gtd.layers.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeAction
import org.freeplane.features.gtd.layers.GtdLayerController
import org.freeplane.features.gtd.layers.GtdLayerType
import org.freeplane.features.gtd.layers.GtdLayerType.*
import org.freeplane.features.gtd.node.GtdNodeModel
import java.awt.event.ActionEvent

class SetGtdLayerTypeAction(val type : GtdLayerType) : AMultipleGtdNodeAction(generateKey(type), generateText(type)) {

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeModel) {
        GtdLayerController.createLayer(node)
        GtdLayerController.setLayerType(node, type)
    }

    companion object {
        fun generateKey(type : GtdLayerType) = "${SetGtdLayerTypeAction::class.simpleName}.$type"

        fun generateText(type : GtdLayerType) = when(type) {
            Layer0 -> "Layer-0"
            Layer1 -> "Layer-1"
            LayerLocal -> "Layer-local"
        }
    }
}