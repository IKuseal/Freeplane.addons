package org.freeplane.features.gtd.miscellaneous

import org.freeplane.features.custom.map.extensions.TreeSpread
import org.freeplane.features.custom.synchronizednodes.SyncNodeController
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.layers.GtdLayerController
import org.freeplane.features.gtd.layers.layer
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.logistics.addressing.AddressController

object GtdMiscellaneousController {
    fun init() {
        GtdController.run {
            addAction(CreateGtdSegmentAction())
            addAction(CreateGtdScopeSegmentAction())
        }
        SyncNodeController.addSyncHandler(GtdSyncNodeSyncHandler)
    }

    fun createGtdSegment(gtdNodeC: GtdNodeCover) {
        AddressController.switchAddress(gtdNodeC.node, true)
        if(gtdNodeC.layer == null)
            GtdLayerController.switchLayer(gtdNodeC.gtdNodeModel, true, TreeSpread.OWN)
    }
}