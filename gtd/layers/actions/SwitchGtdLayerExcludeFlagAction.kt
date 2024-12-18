package org.freeplane.features.gtd.layers.actions

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.AMultipleGtdNodeAction
import org.freeplane.features.gtd.layers.GtdLayerController
import org.freeplane.features.gtd.layers.layerExcludeFlag
import org.freeplane.features.gtd.map.GtdMapController
import org.freeplane.features.gtd.node.GtdNodeModel
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SwitchGtdLayerExcludeFlagAction : AMultipleGtdNodeAction("Gtd.SwitchGtdLayerExcludeFlagAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeModel) {
        setSelected()
        GtdLayerController.switchExcludeFlag(node, !isSelected)
    }

    override fun setSelected() {
        isSelected = GtdMapController.selectedGtdNodeSafe?.layerExcludeFlag ?: false
    }
}