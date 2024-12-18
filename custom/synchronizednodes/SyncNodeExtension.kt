package org.freeplane.features.custom.synchronizednodes

import org.freeplane.core.extension.IExtension
import org.freeplane.features.custom.map.getExt
import org.freeplane.features.custom.map.name
import org.freeplane.features.custom.map.putExt
import org.freeplane.features.map.NodeModel

data class SyncNodeExtension(val mapId : String, val nodeId : String) : IExtension {
    constructor(node : NodeModel) : this(node.map!!.name, node.id)
}

var NodeModel.syncNodeExtension : SyncNodeExtension?
    get() = getExt(SyncNodeExtension::class)
    set(value) {
        putExt(SyncNodeExtension::class, value)
    }

fun NodeModel.hasSyncNodeExtension() = syncNodeExtension != null