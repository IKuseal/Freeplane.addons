package org.freeplane.features.custom.workspread

import org.freeplane.features.custom.map.extensions.TreeSpread.*
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.map.GtdMapController
import org.freeplane.features.gtd.map.cover.GtdMapCoverController

object WorkSpreadController {
    var workSpread = OWN
        set(value) {
            field = value
            GtdController.fireMapChanged(
                WorkSpreadController::class.java, GtdController.currentMap,
                GtdMapController.gtdMapEventUndefinedProp, setsDirtyFlag = false)
        }

    fun init() {
        GtdController.addUiBuilder(SET_GTD_WORK_SPREAD_MENU_BUILDER, SetGtdWorkSpreadMenuBuilder)
    }

    fun resetSpread() {
        workSpread = OWN
    }
}