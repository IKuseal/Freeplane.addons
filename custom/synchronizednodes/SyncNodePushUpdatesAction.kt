package org.freeplane.features.custom.synchronizednodes

import org.freeplane.core.ui.AMultipleNodeAction
import org.freeplane.features.map.NodeModel
import java.awt.event.ActionEvent

class SyncNodePushUpdatesAction : AMultipleNodeAction("Custom.SyncNodePushUpdatesAction") {
    private lateinit var nodes : MutableList<NodeModel>

    override fun actionPerformed(e: ActionEvent?) {
        nodes = arrayListOf()
        super.actionPerformed(e)
        SyncNodeController.pushUpdates(nodes)
    }

    override fun actionPerformed(e: ActionEvent?, node: NodeModel) {
        node.takeIf { it.hasSyncNodeExtension() }
            ?.let { nodes.add(it) }
    }
}