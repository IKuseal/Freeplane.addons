package org.freeplane.features.custom.map.readwrite

import org.freeplane.core.util.Compat
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.map.MapLifecycleState
import org.freeplane.features.map.MapModel
import org.freeplane.features.map.mindmapmode.MMapModel
import java.io.File

object MapLoaderFacade {
    fun createMap(createNewRoot : Boolean = true) : MapModel =
        MMapModel(GlobalFrFacade.mapController.duplicator()).apply {
            if(createNewRoot) createNewRoot()
            isSaved = false
            url = null
        }

    fun announceMapCreation(map : MapModel) {
        GlobalFrFacade.mapController.fireMapCreated(map)
    }

    fun publishMap(map : MMapModel) {
        GlobalFrFacade.mapController.let {
            it.addLoadedMap(map)
            map.enableAutosave()
            it.createMapView(map)
        }
    }

    private fun loadMap(map : MapModel, file : File) : MapModel {
        map.url = Compat.fileToUrl(file)
        GlobalFrFacade.fileManager.loadTree(map, file)
        map.lifecycleState = MapLifecycleState.LOADED
        return map

//        val root = GlobalFrFacade.fileManager.loadTree(map, file)
//
//        return root.map
    }

    fun loadMap(file : File) = loadMap(createMap(false), file)
}