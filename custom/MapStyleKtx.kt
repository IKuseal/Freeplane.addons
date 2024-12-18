package org.freeplane.features.custom

import org.freeplane.features.custom.map.MapLifecycleState
import org.freeplane.features.custom.map.name
import org.freeplane.features.custom.map.readwrite.standardUpToStartedConductor
import org.freeplane.features.custom.space.SpaceController
import org.freeplane.features.map.MapModel
import org.freeplane.features.styles.MapStyle
import org.freeplane.features.styles.MapStyleModel
import org.freeplane.features.url.mindmapmode.MapLifecycleConductor
import java.io.File
import java.net.URI

const val gtdMapStyleTemplatePath = "file:/C:/Users/serge/Dropbox/freeplane/templates/gtdBasedDarkGruvbox.mm"

private fun replaceMapStyleConductor(file : File) = MapLifecycleConductor().apply {
    load(file)
    targetState = MapLifecycleState.STARTED
    isLookupCash = true
}

val mapStyleController get() = MapStyle.getController()

val MapModel.styleModel get() = MapStyleModel.getExtension(this)!!

val MapModel.followedMapPath : String? get() =
    mapStyleController.getProperty(this, MapStyleModel.FOLLOWED_TEMPLATE_LOCATION_PROPERTY)

fun replaceMapStylesInSpace(followedPath : String) {
    SpaceController.mapFiles.forEach {
        val map = replaceMapStyleConductor(it).doJob()

        map.followedMapPath?.takeIf { it == followedPath } ?: return@forEach

        println("R: ${map.name}")

        mapStyleController.replaceStyles(URI(followedPath), map, false, false)

        GlobalFrFacade.save(map)
    }
}