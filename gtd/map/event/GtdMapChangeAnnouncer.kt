package org.freeplane.features.gtd.map.event

import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.core.GtdEventProp
import org.freeplane.features.gtd.map.GtdMap
import org.freeplane.features.gtd.map.gtdMap
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.features.map.IMapChangeListener
import org.freeplane.features.map.NodeDeletionEvent
import org.freeplane.features.map.NodeModel
import org.freeplane.features.map.NodeMoveEvent

object GtdMapChangeAnnouncer : IGtdMapChangeAnnouncer, IMapChangeListener {
    private val listeners : MutableCollection<IGtdMapChangeListener> = arrayListOf()

    // as announcer ***************************************************************
    override fun addMapChangeListener(listener : IGtdMapChangeListener) {
        listeners.add(listener)
    }

    override fun fireNodeChange(node: GtdNodeModel, prop: GtdEventProp) {
        GtdController.nodeChanged(node.node, prop)
        fireNodeChangeInternal(node, prop)
    }

    override fun fireMapChange(map: GtdMap, prop: GtdEventProp, setsDirtyFlag : Boolean) {
        GtdController.fireMapChanged(GtdMapChangeAnnouncer::class.java, map.map, prop,
            null, null, setsDirtyFlag)
        fireMapChangeInternal(map, prop, setsDirtyFlag)
    }

    // listening freeplane ************************************************************************
    override fun onNodeDeleted(nodeDeletionEvent: NodeDeletionEvent?) {
        nodeDeletionEvent?.parent?.map?.let { fireMapStructureChange(it.gtdMap) }
    }

    override fun onNodeInserted(parent: NodeModel?, child: NodeModel?, newIndex: Int) {
        parent?.map?.let { fireMapStructureChange(it.gtdMap) }
    }

    override fun onNodeMoved(nodeMoveEvent: NodeMoveEvent?) {
        nodeMoveEvent?.oldParent?.map?.let { fireMapStructureChange(it.gtdMap) }
    }

    private fun fireMapStructureChange(map: GtdMap) {
        listeners.forEach { it.onMapStructureChange(map) }
    }

    private fun fireNodeChangeInternal(node: GtdNodeModel, prop: GtdEventProp) {
        listeners.forEach { it.onMapNodeChange(GtdNodeChangeEvent(node, prop)) }
    }

    private fun fireMapChangeInternal(map: GtdMap, prop: GtdEventProp, setsDirtyFlag : Boolean) {
        listeners.forEach { it.onMapChange(GtdMapChangeEvent(map, prop)) }
    }
}