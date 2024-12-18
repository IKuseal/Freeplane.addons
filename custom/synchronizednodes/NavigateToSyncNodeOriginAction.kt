package org.freeplane.features.custom.synchronizednodes

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.actions.ANodeModelAction
import org.freeplane.features.custom.map.readwrite.standardUpToResumedConductor
import org.freeplane.features.custom.space.SpaceController
import org.freeplane.features.map.NodeModel
import java.awt.event.ActionEvent

class NavigateToSyncNodeOriginAction : ANodeModelAction("Custom.NavigateToSyncNodeOriginAction") {
    override fun actionPerformed(e: ActionEvent?, node: NodeModel) {
        val syncNodeExtension = node.syncNodeExtension ?: return
        val mapFile = SpaceController.getMapFile(syncNodeExtension.mapId)!!
        val originMap = standardUpToResumedConductor(mapFile).doJob()
        val originNode = originMap.getNodeForID(syncNodeExtension.nodeId)
        GlobalFrFacade.mapController.select(originNode)
    }
}