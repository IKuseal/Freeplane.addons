package org.freeplane.features.custom.actions

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.customjava.AActionsEnabler
import org.freeplane.features.map.IMapChangeListener
import org.freeplane.features.map.IMapSelectionListener
import org.freeplane.features.map.MapChangeEvent
import org.freeplane.features.map.MapModel

abstract class AMapChangeActionsEnabler : AActionsEnabler, IMapChangeListener, IMapSelectionListener {
    constructor(prop : String) : super(prop) {
        GlobalFrFacade.mapController.addUIMapChangeListener(this)
        GlobalFrFacade.mapViewController.addMapSelectionListener(this)
    }

    override fun mapChanged(event: MapChangeEvent?) {
        setActionEnabled()
    }

    override fun afterMapChange(oldMap: MapModel?, newMap: MapModel?) {
        setActionEnabled()
    }
}