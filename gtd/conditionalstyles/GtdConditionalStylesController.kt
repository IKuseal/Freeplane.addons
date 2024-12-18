package org.freeplane.features.gtd.conditionalstyles

import org.freeplane.features.custom.map.drawMapView
import org.freeplane.features.gtd.node.gtdNodeCover
import org.freeplane.features.map.NodeModel
import org.freeplane.features.styles.IStyle


object GtdConditionalStylesController {

    fun getStyles(node : NodeModel) : Collection<IStyle> {
        val mapView = drawMapView ?: return listOf()
        val gtdNodeC = node.createNodeCover(mapView.mapCover).gtdNodeCover

        return GtdNodeCoverStylesAccessor(gtdNodeC).getConditionalStyles()
    }
}