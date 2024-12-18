package org.freeplane.features.custom.map

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.map.MapController
import org.freeplane.features.map.NodeModel

fun MapController.clearChildren(parent : NodeModel) {
    GlobalFrFacade.mapController.deleteNodes(parent.children.toList())
}