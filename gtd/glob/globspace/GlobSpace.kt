package org.freeplane.features.gtd.glob.globspace

import org.freeplane.features.custom.mapIdFromUriToNode
import org.freeplane.features.custom.space.SpaceController
import org.freeplane.features.gtd.allmaps.attachedToGtdGlobMaps
import org.freeplane.features.gtd.glob.GlobModuleData
import org.freeplane.features.gtd.glob.scheme.ModulePlaceholder

object GlobSpace {
    fun findModules() : Collection<GlobModuleData> = arrayListOf<GlobModuleData>().also { list ->
        SpaceController.attachedToGtdGlobMaps.forEach {
            MapModulesImporter.import(it).let(list::addAll)
        }
    }

    fun findModule(modulePlaceholder: ModulePlaceholder) : GlobModuleData? {
        val mapId = mapIdFromUriToNode(modulePlaceholder.moduleUri)
        val mapFile = SpaceController.getMap(mapId)!!
        val modules = MapModulesImporter.import(mapFile)
        val id = modulePlaceholder.id
        return modules.firstOrNull { it.id ==  id}
    }
}