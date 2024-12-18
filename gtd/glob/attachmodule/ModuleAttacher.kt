package org.freeplane.features.gtd.glob.attachmodule

import org.freeplane.features.custom.map.clearChildren
import org.freeplane.features.custom.map.stream
import org.freeplane.features.custom.synchronizednodes.SyncNodeExtension
import org.freeplane.features.custom.synchronizednodes.syncNodeExtension
import org.freeplane.features.custom.treestream.fold
import org.freeplane.features.gtd.clipboard.GtdClipboardController
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.glob.GlobModuleData
import org.freeplane.features.gtd.glob.scheme.ModulePlaceholder
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.node.currentGtdNodeCover
import org.freeplane.features.gtd.node.gtdNode
import org.freeplane.features.map.NodeModel

object ModuleAttacher {
    fun attach(moduleData: GlobModuleData, modulePlaceholder: ModulePlaceholder) {
        val merge: (NodeModel, NodeModel) -> Unit = { n1, n2 ->
            GtdClipboardController.copy(n1.currentGtdNodeCover)
            GtdClipboardController.pasteIntents(n2.gtdNode)
        }

        merge(moduleData.node, modulePlaceholder.node)

        modulePlaceholder.node.gtdNode.intents.forEach { it.gcsType = GcsType.GLOB }

        val modulePlaceholderNode = modulePlaceholder.node

        modulePlaceholderNode.syncNodeExtension = SyncNodeExtension(moduleData.node)

        moduleData.node.children.forEach { n1 ->
            val nodeXml = GtdController.copyAsXml(n1)
            val n2 = GtdController.pasteXml(modulePlaceholderNode, nodeXml)

            // sync installing
            val n1All = n1.stream().fold(arrayListOf<NodeModel>()) { list, it -> list.add(it); list }
            val n2All = n2.stream().fold(arrayListOf<NodeModel>()) { list, it -> list.add(it); list }

            for(i in 0 until n1All.size) {
                n2All[i].syncNodeExtension = SyncNodeExtension(n1All[i])
            }
        }
    }

    fun reattach(moduleData: GlobModuleData, modulePlaceholder: ModulePlaceholder) {
        GtdController.mapController.clearChildren(modulePlaceholder.node)
        attach(moduleData, modulePlaceholder)
    }
}
