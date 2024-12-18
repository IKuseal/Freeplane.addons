package org.freeplane.features.custom.synchronizednodes

import org.freeplane.features.map.MapModel
import org.freeplane.features.map.NodeModel

class SyncTask(syncNodeExtension: SyncNodeExtension) {
    constructor(syncNode : NodeModel) : this(syncNode.syncNodeExtension!!) {
        this.syncNode = syncNode
    }

    lateinit var syncNode : NodeModel

    val originId = syncNodeExtension.nodeId
    val originMapId = syncNodeExtension.mapId
    lateinit var originMap : MapModel
    lateinit var originNode : NodeModel
}