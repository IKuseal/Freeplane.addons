package org.freeplane.features.custom.synchronizednodes

import org.freeplane.features.map.NodeModel

interface SyncNodeSyncHandler {
    fun onPush(originNode : NodeModel, syncNode : NodeModel) {}
    fun onPull(originNode : NodeModel, syncNode : NodeModel) {}
    fun onSync(originNode : NodeModel, syncNode : NodeModel) {}
}