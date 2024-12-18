package org.freeplane.features.custom.map

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.map.MapModel
import org.freeplane.view.swing.map.MapView

val MapModel.currentMapCover get() =
    if (!isMapViewSet) initialMapCover!! else GlobalFrFacade.currentMapView.mapCover!!

val MapModel.name get() = file?.name?.removeSuffix(".mm") ?: ""

val String.fileNameFromId get() = "$this.mm"

val MapModel.views : Collection<MapView> get() =
    GlobalFrFacade.mapViewController.mapViewVector
        .filter { it.model == this }