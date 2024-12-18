package org.freeplane.features.custom.map

import org.freeplane.view.swing.map.MapView

var drawMapView : MapView? = null
    get() {
        val result = field ?: return null

        return if(result.isAmongOpened) result else run {
            field = null
            null
        }
    }

val MapView.isAmongOpened get() = model.views.any { it === this }