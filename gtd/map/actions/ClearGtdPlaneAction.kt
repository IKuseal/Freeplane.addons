package org.freeplane.features.gtd.map.actions

import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.tag.GtdPlane
import org.freeplane.features.gtd.map.GtdMapController
import org.freeplane.features.gtd.map.currentGtdMap
import java.awt.event.ActionEvent

class ClearGtdPlaneAction(val plane : GtdPlane)
    : AGtdAction(generateKey(plane), plane.name) {

    override fun actionPerformed(e: ActionEvent?) {
        GtdMapController.clearPlane(currentGtdMap, plane)
    }

    companion object {
        fun generateKey(plane : GtdPlane) = ClearGtdPlaneAction::class.simpleName + "." + plane.name
    }
}