package org.freeplane.features.gtd.layers.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeAction
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.layers.GtdLayerController
import org.freeplane.features.gtd.node.GtdNodeModel
import java.awt.event.ActionEvent

class SetGcsTypeAction(val type : GcsType) : AMultipleGtdNodeAction(generateKey(type), type.name) {

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeModel) {
        GtdLayerController.createLayer(node)
        GtdLayerController.setLayerGcsType(node, type)
    }

    companion object {
        fun generateKey(type : GcsType) = "${SetGcsTypeAction::class.simpleName}.$type"
    }
}