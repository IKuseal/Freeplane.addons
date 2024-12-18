package org.freeplane.features.custom.actions

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.customjava.AActionsEnabler
import org.freeplane.features.map.*

abstract class ANodeChangeActionsEnabler : AActionsEnabler, INodeChangeListener, INodeSelectionListener,
    IMapChangeListener {

    constructor(prop : String) : super(prop) {
        val mapController = GlobalFrFacade.mapController

        mapController.addUINodeChangeListener(this)
        mapController.addUIMapChangeListener(this)
        mapController.addNodeSelectionListener(this)
    }

    override fun nodeChanged(event: NodeChangeEvent?) {
        setActionEnabled()
    }

    override fun onDeselect(node: NodeModel?) {}

    override fun onSelect(node: NodeModel?) {
        runner.runLater()
    }

    override fun mapChanged(event: MapChangeEvent?) {
        setActionEnabled()
    }

    override fun onNodeDeleted(nodeDeletionEvent: NodeDeletionEvent?) {
        setActionEnabled()
    }

    override fun onNodeInserted(parent: NodeModel?, child: NodeModel?, newIndex: Int) {
        setActionEnabled()
    }

    override fun onNodeMoved(nodeMoveEvent: NodeMoveEvent?) {
        setActionEnabled()
    }

    override fun onPreNodeMoved(nodeMoveEvent: NodeMoveEvent?) {}

    override fun onPreNodeDelete(nodeDeletionEvent: NodeDeletionEvent?) {
        setActionEnabled()
    }
}