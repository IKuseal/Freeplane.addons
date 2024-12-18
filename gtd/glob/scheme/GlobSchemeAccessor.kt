package org.freeplane.features.gtd.glob.scheme

import org.freeplane.features.custom.map.MapLifecycleState
import org.freeplane.features.custom.space.SpaceController
import org.freeplane.features.map.MapModel
import org.freeplane.features.url.mindmapmode.MapLifecycleConductor
import java.io.File

class GlobSchemeAccessor(val schemeMapId : String) {
    fun newMap() : MapModel =
        MapLifecycleConductor()
            .run {
                load(this@GlobSchemeAccessor.file)
                targetState = MapLifecycleState.LOADED
                doJob()
            }
            .apply {
                url = null
            }

    private val file : File
        get() = SpaceController.getMapFile(schemeMapId)!!
}