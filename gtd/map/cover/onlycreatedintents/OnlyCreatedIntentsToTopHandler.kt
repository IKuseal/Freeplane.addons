package org.freeplane.features.gtd.map.cover.onlycreatedintents

import org.freeplane.features.gtd.core.FrFacade
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.intent.GtdIntent
import org.freeplane.features.gtd.map.cover.GtdMapCover
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.node.currentGtdNodeCover
import org.freeplane.features.map.INodeSelectionListener
import org.freeplane.features.map.NodeModel
import java.util.WeakHashMap

object OnlyCreatedIntentsToTopHandler : INodeSelectionListener {
    init {
        GtdController.addNodeSelectionListener(this)
    }

    // api ****************************************************************************************************
    fun addNodeWithOnlyCreatedIntent(node: GtdNodeCover, intent: GtdIntent) {
        node.gtdMapCover.addOnlyCreatedIntent(node, intent)
    }

    // only created intents ************************************************************************
    private val onlyCreatedIntentsMap : MutableMap<GtdMapCover, MutableList<GtdNodeCover>> = WeakHashMap()

    private val GtdMapCover.onlyCreatedIntents : MutableList<GtdNodeCover>?
        get() = onlyCreatedIntentsMap[this]

    private val GtdMapCover.ensureOnlyCreatedIntents : MutableList<GtdNodeCover>
        get() = onlyCreatedIntents
            ?: arrayListOf<GtdNodeCover>().also { onlyCreatedIntentsMap[this] = it }

    private fun GtdMapCover.addOnlyCreatedIntent(gtdNodeC : GtdNodeCover, intent : GtdIntent) {
        gtdNodeC.intentToTop = intent
        ensureOnlyCreatedIntents.add(gtdNodeC)
        GtdMapCoverController.updateNode(gtdNodeC)
    }

    private fun GtdMapCover.removeOnlyCreatedIntent(gtdNodeC : GtdNodeCover) {
        gtdNodeC.intentToTop = null
        ensureOnlyCreatedIntents.remove(gtdNodeC)
        GtdMapCoverController.updateNode(gtdNodeC)
    }

    // * * *

    private fun onSelectionChange() {
        val gtdMapC = GtdMapCoverController.currentMapCover
        val onlyCreateIntents = gtdMapC.onlyCreatedIntents?.takeIf { it.isNotEmpty() }?.toList() ?: return
        val selection = selection

        onlyCreateIntents.forEach {
            if(!selection.contains(it)) gtdMapC.removeOnlyCreatedIntent(it)
        }
    }

    // selection listening ******************************************************************************
    override fun onDeselect(node: NodeModel?) {
        onSelectionChange()
    }

    override fun onSelect(node: NodeModel?) {
        onSelectionChange()
    }

    val selection get() = FrFacade.selectedNodes.map { it.currentGtdNodeCover }
}