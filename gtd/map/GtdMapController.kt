package org.freeplane.features.gtd.map

import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.core.GtdEventProp
import org.freeplane.features.gtd.core.gtdEventProp
import org.freeplane.features.gtd.intent.GtdIntentController
import org.freeplane.features.gtd.layers.GtdLayerController
import org.freeplane.features.gtd.map.actions.HardResetGtdNodeAction
import org.freeplane.features.gtd.map.event.GtdMapChangeAnnouncer
import org.freeplane.features.gtd.map.event.IGtdMapChangeAnnouncer
import org.freeplane.features.custom.workspread.WorkSpreadController
import org.freeplane.features.gtd.core.FrFacade
import org.freeplane.features.gtd.tag.GtdPlane
import org.freeplane.features.gtd.map.actions.ClearGtdPlaneMenuBuilder
import org.freeplane.features.gtd.module.GtdModuleController
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.features.gtd.node.forEach
import org.freeplane.features.gtd.node.gtdNode
import org.freeplane.features.gtd.readwrite.GtdNodeBuilder

object GtdMapController : IGtdMapChangeAnnouncer by GtdMapChangeAnnouncer {

    val gtdNodeEventProp = GtdEventProp(gtdEventProp)
    val gtdNodeEventUndefinedProp = GtdEventProp(gtdNodeEventProp)
    val gtdNodeEventSpreadProp = GtdEventProp(gtdNodeEventProp)

    val gtdMapEventProp = GtdEventProp(gtdEventProp)
    val gtdMapEventUndefinedProp = GtdEventProp(gtdMapEventProp)

    fun init() {
        WorkSpreadController.init()

        GtdController.run {
            addMapChangeListener(GtdMapChangeAnnouncer)

            GtdNodeBuilder.registerBy(readManager, writeManager)
//            MigrationGtdNodeBuilder.registerBy(readManager)
        }

        initActions()
    }

    private fun initActions() {
        GtdController.apply {
            addAction(HardResetGtdNodeAction())
            addUiBuilder(ClearGtdPlaneMenuBuilder.key, ClearGtdPlaneMenuBuilder)
        }
    }

    // GtdNode managing ************************************************************************************
    fun resetNode(gtdNode: GtdNodeModel) {
        GtdIntentController.clearIntents(gtdNode)
        GtdModuleController.switchGtdModule(gtdNode, false)
        GtdLayerController.resetLayerSpreads(gtdNode)
        GtdLayerController.switchExcludeFlag(gtdNode, false)
    }

    fun clearPlane(gtdMap : GtdMap, plane : GtdPlane) {
        gtdMap.root.forEach {
            intents
                .filter { it.containsInAllTags(plane) }
                .forEach {
                    GtdIntentController.removeIntent(this, it)
                }
        }
    }

    // heap *************************************************************************************************
    val selectedGtdNode get() = GtdController.selectedNode.gtdNode
    val selectedGtdNodeSafe get() = GtdController.selectedNodeSafe?.gtdNode

    val selectedGtdNodes get() = GtdController.selectedNodes.map { it.gtdNode }
}

val currentGtdMap get() = FrFacade.currentMap.gtdMap