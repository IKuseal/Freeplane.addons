package org.freeplane.features.gtd.core

import org.freeplane.features.anki.aiFreeplane.frFacade.FrEventsAnnouncer
import org.freeplane.features.gtd.configuration.isGtdActive
import org.freeplane.features.gtd.configuration.isGtdInstalled
import org.freeplane.features.map.*
import org.freeplane.view.swing.map.MapView
import java.awt.Component

object FrEventsAnnouncer : FrEventsAnnouncer() {
    override fun mapChanged(event: MapChangeEvent?) {
        if(!checkRelevance(event?.map)) return
        super.mapChanged(event)
    }

    override fun onNodeDeleted(nodeDeletionEvent: NodeDeletionEvent?) {
        if(!checkRelevance(nodeDeletionEvent?.node)) return
        super.onNodeDeleted(nodeDeletionEvent)
    }

    override fun onNodeInserted(parent: NodeModel?, child: NodeModel?, newIndex: Int) {
        if(!checkRelevance(parent)) return
        super.onNodeInserted(parent, child, newIndex)
    }

    override fun onNodeMoved(nodeMoveEvent: NodeMoveEvent?) {
        if(!checkRelevance(nodeMoveEvent?.child)) return
        super.onNodeMoved(nodeMoveEvent)
    }

    override fun onPreNodeMoved(nodeMoveEvent: NodeMoveEvent?) {
        if(!checkRelevance(nodeMoveEvent?.child)) return
        super.onPreNodeMoved(nodeMoveEvent)
    }

    override fun onPreNodeDelete(nodeDeletionEvent: NodeDeletionEvent?) {
        if(!checkRelevance(nodeDeletionEvent?.node)) return
        super.onPreNodeDelete(nodeDeletionEvent)
    }

    override fun nodeChanged(event: NodeChangeEvent?) {
        if(!checkRelevance(event?.node)) return
        super.nodeChanged(event)
    }

    override fun onDeselect(node: NodeModel?) {
        if(!checkRelevance(node)) return
        super.onDeselect(node)
    }

    override fun onSelect(node: NodeModel?) {
        if(!checkRelevance(node)) return
        super.onSelect(node)
    }

    override fun afterMapChange(oldMap: MapModel?, newMap: MapModel?) {
        if(!checkRelevance(newMap)) return
        super.afterMapChange(oldMap, newMap)
    }

    override fun beforeMapChange(oldMap: MapModel?, newMap: MapModel?) {
        if(!checkRelevance(newMap)) return
        super.beforeMapChange(oldMap, newMap)
    }

    override fun onCreate(map: MapModel?) {
        if(!checkHalfRelevance(map)) return
        super.onCreate(map)
    }

    override fun onRemove(map: MapModel?) {
        if(!checkRelevance(map)) return
        super.onRemove(map)
    }

    override fun propertyChanged(propertyName: String?, newValue: String?, oldValue: String?) {
        if(!checkRelevance(GtdController.currentMap)) return
        super.propertyChanged(propertyName, newValue, oldValue)
    }

    override fun afterViewChange(oldView: Component?, newView: Component?) {
        if(newView == null) return
        val map = (newView as MapView).model
        if(!checkRelevance(map)) return
        super.afterViewChange(oldView, newView)
    }

    override fun afterViewClose(oldView: Component?) {}

    override fun afterViewCreated(oldView: Component?, newView: Component?) {
        if(newView == null) return
        val map = (newView as MapView).model
        if(!checkRelevance(map)) return
        super.afterViewCreated(oldView, newView)
    }

    override fun beforeViewChange(oldView: Component?, newView: Component?) {
        if(newView == null) return
        val map = (newView as MapView).model
        if(!checkRelevance(map)) return
        super.beforeViewChange(oldView, newView)
    }

    private fun checkRelevance(node : NodeModel?) = checkRelevance(node?.map)

    private fun checkRelevance(map : MapModel?) = map?.isGtdActive ?: false

    private fun checkHalfRelevance(map : MapModel?) = map?.isGtdInstalled ?: false
}