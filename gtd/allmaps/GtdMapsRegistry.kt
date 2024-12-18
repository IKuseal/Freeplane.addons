package org.freeplane.features.gtd.allmaps

import org.freeplane.features.custom.space.SpaceController
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.map.NodeModel

object GtdMapsRegistry {
    fun insertListOfGtdIsInstalledMaps(root : NodeModel) {
        val map = root.map
        SpaceController.gtdInstalledMaps.forEach {
            val file = it.file
            NodeModel(file.name, map).also {
                GtdController.setLink(it, file.toURI().toString())
                GtdController.mapController.insertNode(it, root)
            }
        }
    }

    fun insertListOfGtdIsAttachedToGlobMaps(root : NodeModel) {
        val map = root.map
        SpaceController.attachedToGtdGlobMaps.forEach {
            val file = it.file
            NodeModel(file.name, map).also {
                GtdController.setLink(it, file.toURI().toString())
                GtdController.mapController.insertNode(it, root)
            }
        }
    }
}