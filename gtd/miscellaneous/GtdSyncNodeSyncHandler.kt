package org.freeplane.features.gtd.miscellaneous

import org.freeplane.features.custom.synchronizednodes.SyncNodeSyncHandler
import org.freeplane.features.gtd.clipboard.GtdClipboardController
import org.freeplane.features.gtd.node.currentGtdNodeCover
import org.freeplane.features.gtd.node.gtdNode
import org.freeplane.features.map.NodeModel

object GtdSyncNodeSyncHandler : SyncNodeSyncHandler {
    override fun onPush(originNode: NodeModel, syncNode: NodeModel) {
        GtdClipboardController.deepCopy(syncNode.currentGtdNodeCover)
        GtdClipboardController.pasteIntents(originNode.gtdNode)
    }
}