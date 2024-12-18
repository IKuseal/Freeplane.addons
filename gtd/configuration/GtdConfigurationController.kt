package org.freeplane.features.gtd.configuration

import org.freeplane.features.gtd.configuration.actions.AttachGtdToGlobalAction
import org.freeplane.features.gtd.configuration.actions.EnableGtdAction
import org.freeplane.features.gtd.configuration.actions.InstallGtdAction
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.core.GtdEventProp
import org.freeplane.features.gtd.core.gtdEventProp
import org.freeplane.features.gtd.map.GtdMapController
import org.freeplane.features.gtd.map.gtdMap
import org.freeplane.features.map.MapModel

object GtdConfigurationController {
    val gtdConfigurationEventProp = GtdEventProp(gtdEventProp)

    fun init() {
        GtdController.run {
            addAction(InstallGtdAction())
            addAction(EnableGtdAction())
            addAction(AttachGtdToGlobalAction())

            GtdConfigurationBuilder.registerBy(readManager, writeManager)
        }
    }

    fun installGtd(map : MapModel) {
        map.createGtdConfiguration()
        fire(map)
    }

    fun enableGtd(map : MapModel, enabled : Boolean = true) {
        map.gtdConfiguration!!.gtdEnabled = enabled
        fire(map)
    }

    fun attachGtdToGlobal(map : MapModel, toAttach : Boolean = true) {
        map.gtdConfiguration!!.attachedToGlobal = toAttach
        fire(map)
    }

    private fun fire(map : MapModel) {
        GtdMapController.fireMapChange(map.gtdMap, gtdConfigurationEventProp)
    }
}